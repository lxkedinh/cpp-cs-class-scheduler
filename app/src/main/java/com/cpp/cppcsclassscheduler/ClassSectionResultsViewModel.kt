package com.cpp.cppcsclassscheduler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ClassSectionResultsViewModel : ViewModel() {

    val repository = CsClassRepository.get()
}
