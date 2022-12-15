package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpp.cppcsclassscheduler.calendar_api.CalendarClient
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.database.ShoppingCartRepository
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.services.calendar.Calendar.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.BufferedReader

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

    fun convertClassesToEvents(classes: List<CsClass>): Events {
        TODO("Not implemented yet")
    }
}