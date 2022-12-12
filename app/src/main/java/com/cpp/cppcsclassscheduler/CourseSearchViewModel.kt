package com.cpp.cppcsclassscheduler

import androidx.lifecycle.*

class CourseSearchViewModel : ViewModel() {

    private val repository = CsClassRepository.get()

    suspend fun getAllCourseNames(): List<Pair<String,Int>> {
        // create set to prevent duplicates of class names
        val classNameSet = mutableSetOf<Pair<String, Int>>()
        val classes = repository.getAllClasses()
        for (csClass in classes) {
            classNameSet.add(Pair("CS ${csClass.courseId} - ${csClass.name}", csClass.courseId))
        }
        return classNameSet.toList()
    }
}