package com.cpp.cppcsclassscheduler

import androidx.lifecycle.ViewModel

class ClassSectionResultsViewModel : ViewModel() {
    val csClassRepository = CsClassRepository.get()
    val cartRepository = ShoppingCartRepository.get()
}
