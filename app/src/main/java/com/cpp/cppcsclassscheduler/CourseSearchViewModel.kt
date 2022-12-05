package com.cpp.cppcsclassscheduler

import androidx.lifecycle.*

class CourseSearchViewModel : ViewModel() {

    val repository = CsClassRepository.get()
    val courseNames: LiveData<List<String>> = liveData {
        emit(getAllCourses())
    }

    suspend fun getAllCourses(): List<String> {
        // create set to prevent duplicates of class names
        val classNameSet = mutableSetOf<String>()
        val classes = repository.getAllClasses()
        for (csClass in classes) {
            classNameSet.add("CS ${csClass.id} - ${csClass.name}")
        }
        return classNameSet.toList()
    }

    init {

    }
}