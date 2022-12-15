package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpp.cppcsclassscheduler.calendar_api.CalendarClient
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.database.ShoppingCartRepository
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.BufferedReader

private const val TAG = "ShoppingCartViewModel"

class ShoppingCartViewModel: ViewModel() {

    private val cartRepository = ShoppingCartRepository.get()

    fun getShoppingCart(): Flow<List<CsClass>> = cartRepository.getCart()

    fun addCartToCalendar(clientSecretsJSONReader: BufferedReader) {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

    fun deleteCourseFromCart(section: CsClass) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteClassFromCart(section)
        }
    }
}