package com.cpp.cppcsclassscheduler.activities.section_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.database.CsClassRepository
import com.cpp.cppcsclassscheduler.database.ShoppingCartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SectionSelectionViewModel : ViewModel() {
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
