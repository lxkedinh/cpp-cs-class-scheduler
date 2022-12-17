package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpp.cppcsclassscheduler.calendar_api.CalendarClient
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.database.ShoppingCartRepository
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "ShoppingCartViewModel"
private const val TIMEZONE_OFFSET = "-08:00"

class ShoppingCartViewModel: ViewModel() {

    private val cartRepository = ShoppingCartRepository.get()
    private val calendarClient = CalendarClient.get()

    // map class days to actual start date and end date
    private val startingDaysMap: Map<String, String> = mapOf(
        "Mo" to "2023-01-23",
        "Tu" to "2023-01-24",
        "We" to "2023-01-25",
        "Th" to "2023-01-26",
        "Fr" to "2023-01-27",
    )
    private val endDaysMap: Map<String, String> = mapOf(
        "Mo" to "20230509",
        "Tu" to "20230510",
        "We" to "20230511",
        "Th" to "20230512",
        "Fr" to "20230513",
    )

    fun getShoppingCart(): Flow<List<CsClass>> = cartRepository.getCart()

    fun addCartToCalendar() {
        viewModelScope.launch(Dispatchers.IO) {
            // get user's shopping cart
            getShoppingCart().collect { cart ->
                val events = convertClassesToEvents(cart)
                calendarClient.addEventsToCalendar(events)
            }
        }
    }

    fun deleteCourseFromCart(section: CsClass) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteClassFromCart(section)
        }
    }

    private fun convertClassesToEvents(cart: List<CsClass>): List<Event> {
        val events = mutableListOf<Event>()

        for (csClass in cart) {
            // skip fully async classes (ie. classes with TBA meeting days
            if (csClass.days == "TBA") {
                continue
            }

            // parse building/room string
            val (building, room) = csClass.room.split(".")

            val event = Event()
                .setSummary("CS ${csClass.courseId} - ${csClass.name}")
                .setDescription("Section ${csClass.section} with instructor ${csClass.instructor}")
                .setLocation("Building $building Room $room")

            // create date time string for start time
            val firstDay = csClass.days.subSequence(0, 2)
            val startDateString = startingDaysMap[firstDay]

            val startTimeString = "${convertTo24HourFormat(csClass.startTime)}:00$TIMEZONE_OFFSET"
            Log.d(TAG, "${startDateString}T$startTimeString")
            val startDateTime = DateTime("${startDateString}T$startTimeString")

            val start = EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles")
            event.start = start

            // create date time string for end time
            val (endHours, endMinutes) = convertTo24HourFormat(csClass.endTime).split(":")
            val endTimeString = "$endHours:$endMinutes:00-08:00"
            Log.d(TAG, "${startDateString}T$endTimeString")
            val endDateTime = DateTime("${startDateString}T$endTimeString")

            val end = EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles")
            event.end = end

            // create recurrence rule for event
            val lastDay = csClass.days.subSequence(
                csClass.days.length - 2,
                csClass.days.length
            )
            val endDateString = endDaysMap[lastDay]
            val formattedDays = convertDaystoRRULE(csClass.days)

            event.recurrence = listOf("RRULE:FREQ=WEEKLY;BYDAY=$formattedDays;UNTIL=${endDateString}T$endHours${endMinutes}00Z")

            events.add(event)
        }

        return events
    }

    @SuppressLint("NewApi")
    private fun convertTo24HourFormat(time: String): String {
        // pad beginning with 0s so formatter can parse, ie. pad 9:00AM to 09:00AM
        val paddedTime = time.padStart(7, '0')

        return LocalTime.parse(paddedTime,
            // parse input 12 hour time
            DateTimeFormatter.ofPattern("hh:mma", Locale.US)
        )
            // return 24 hour time
            .format( DateTimeFormatter.ofPattern("HH:mm") )
    }

    // Google Calendar recurrence rule requires commas between days like MO,WE for a recurring event
    // every monday and wednesday
    private fun convertDaystoRRULE(days: String): String {
        val s = StringBuilder(days)

        for (i in 2 until days.length step 3) {
            s.insert(i, ',')
        }

        return s.toString()
    }
}