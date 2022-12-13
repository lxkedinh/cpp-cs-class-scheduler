package com.cpp.cppcsclassscheduler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassSectionResultsViewModel : ViewModel() {
    private val csClassRepository = CsClassRepository.get()
    private val cartRepository = ShoppingCartRepository.get()

    suspend fun queryClassInCart(courseId: Int, sectionNumber: Int): Boolean {
        return cartRepository.queryClassInCart(courseId, sectionNumber) != null
    }

    suspend fun getAllSections(courseId: Int): List<CsClass> {
        return csClassRepository.getAllSections(courseId)
    }

    fun addClassToCart(section: CsClass) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.addClassToCart(section)
        }
    }

    fun deleteClassFromCart(section: CsClass) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteClassFromCart(section)
        }
    }
}
