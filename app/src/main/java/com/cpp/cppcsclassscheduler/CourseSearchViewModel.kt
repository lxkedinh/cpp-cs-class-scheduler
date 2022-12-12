package com.cpp.cppcsclassscheduler

import androidx.lifecycle.*

class CourseSearchViewModel : ViewModel() {

    private val repository = CsClassRepository.get()
    val courseNames: LiveData<List<Pair<String,Int>>> = liveData {
        emit(getAllCourses())
    }

    suspend fun getAllCourses(): List<Pair<String,Int>> {
        // create set to prevent duplicates of class names
        val classNameSet = mutableSetOf<Pair<String, Int>>()
        val classes = repository.getAllClasses()
        for (csClass in classes) {
            classNameSet.add(Pair("CS ${csClass.courseId} - ${csClass.name}", csClass.courseId))
        }
        return classNameSet.toList()
    }
}