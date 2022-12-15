package com.cpp.cppcsclassscheduler.calendar_api

import android.content.Context
import android.util.Log
import com.cpp.cppcsclassscheduler.R
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events

private const val TAG = "CalendarClient"

class CalendarClient private constructor(context: Context, val credential: GoogleAccountCredential) {

    private val JSON_FACTORY = GsonFactory.getDefaultInstance()
    private val TOKENS_DIRECTORY_PATH = "tokens"
    val client: Calendar = Calendar.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),
        credential
        )
        .setApplicationName(context.getString(R.string.app_name))
        .build()

    fun test() {
        // List the next 10 events from the primary calendar.
        val now = DateTime(System.currentTimeMillis())
        val events: Events = client.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items: List<Event> = events.getItems()
        if (items.isEmpty()) {
            Log.d(TAG,"No upcoming events found.")
        } else {
            Log.d(TAG, "Upcoming events")
            for (event in items) {
                var start: DateTime? = event.getStart().getDateTime()
                if (start == null) {
                    start = event.getStart().getDate()
                }
                Log.d(TAG, "${event.summary} $start")
            }
        }
    }

    companion object {
        private var INSTANCE: CalendarClient? = null
        fun initialize(context: Context, credential: GoogleAccountCredential) {
            if (INSTANCE == null) {
                INSTANCE = CalendarClient(context, credential)
            }
        }

        fun get(): CalendarClient {
            return INSTANCE ?:
            throw IllegalStateException("Calendar Client must be initialized")
        }

        val CALENDAR_SCOPES = listOf(CalendarScopes.CALENDAR_EVENTS)
    }


}