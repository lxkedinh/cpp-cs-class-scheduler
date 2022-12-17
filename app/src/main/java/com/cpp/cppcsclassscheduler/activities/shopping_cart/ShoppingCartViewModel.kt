package com.cpp.cppcsclassscheduler.activities.shopping_cart

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


private const val TAG = "ShoppingCartViewModel"

class ShoppingCartViewModel: ViewModel() {

    private val cartRepository = ShoppingCartRepository.get()
    private val calendarClient = CalendarClient.get()

    fun getShoppingCart(): Flow<List<CsClass>> = cartRepository.getCart()

    fun addCartToCalendar() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: Convert
        }
    }

    fun deleteCourseFromCart(section: CsClass) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteClassFromCart(section)
        }
    }

    fun convertClassesToEvents(cart: List<CsClass>): List<Event> {

        return cart.map { csClass ->

            val event = Event()
                .setSummary("CS ${csClass.courseId} - ${csClass.name}")
                .setLocation("${csClass.room}")
                .setDescription("Section ${csClass.section} with ${csClass.instructor}")
            event

            val startDateTime = DateTime(csClass.startTime)

            val start = EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Los_Angeles")
            event.setStart(start)

            val endDateTime = DateTime(csClass.endTime)

            val end = EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Los_Angeles")
            event.setEnd(end)
        }
    }

}