package com.cpp.cppcsclassscheduler.activities.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.database.ShoppingCartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShoppingCartViewModel: ViewModel() {

    private val cartRepository = ShoppingCartRepository.get()

    fun getShoppingCart(): Flow<List<CsClass>> = cartRepository.getCart()

    fun addCartToCalendar() {
        viewModelScope.launch(Dispatchers.IO) {
            TODO("add Google Calendar API code to add classes as events to user's google calendar here")
        }
    }

    fun deleteCourseFromCart(section: CsClass) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteClassFromCart(section)
        }
    }
}