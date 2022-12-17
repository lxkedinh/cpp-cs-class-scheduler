package com.cpp.cppcsclassscheduler.calendar_api

import android.content.Context
import com.cpp.cppcsclassscheduler.R
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event

private const val TAG = "CalendarClient"

class CalendarClient private constructor(context: Context, credential: GoogleAccountCredential) {

    private val client: Calendar = Calendar.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),
        credential
        )
        .setApplicationName(context.getString(R.string.app_name))
        .build()

    fun addEventsToCalendar(events: List<Event>) {
        for (event in events) {
            client.events().insert("primary", event).execute()
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