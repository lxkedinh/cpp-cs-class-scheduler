package com.cpp.cppcsclassscheduler.activities.course_selection

import androidx.lifecycle.*
import com.cpp.cppcsclassscheduler.database.CsClassRepository

class CourseSelectionViewModel : ViewModel() {

    private val csClassRepository = CsClassRepository.get()

    suspend fun getAllCourseNames(): List<Pair<String,Int>> {
        // create set to prevent duplicates of class names
        val classNameSet = mutableSetOf<Pair<String, Int>>()
        val classes = csClassRepository.getAllClasses()
        for (csClass in classes) {
            classNameSet.add(Pair("CS ${csClass.courseId} - ${csClass.name}", csClass.courseId))
        }
        return classNameSet.toList()
    }
}