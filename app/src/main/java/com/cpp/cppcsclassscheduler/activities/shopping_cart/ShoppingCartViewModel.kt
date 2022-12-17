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

    private fun addCartToCalendar(cart: List<CsClass>) {
        viewModelScope.launch(Dispatchers.IO) {
            getShoppingCart().collect { cart ->
                addCartToCalendar(cart)
            }
        }

        fun deleteCourseFromCart(section: CsClass) {
            viewModelScope.launch(Dispatchers.IO) {
                cartRepository.deleteClassFromCart(section)
            }
        }
    }
}