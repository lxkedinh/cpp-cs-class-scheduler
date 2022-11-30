package com.cpp.cppcsclassscheduler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

private const val TAG = "ClassSearchActivity"

class ClassSearchActivity : AppCompatActivity() {

    private val repository = CsClassRepository.get()
    private lateinit var classNames: List<String>
    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.class_section_list)
    }

    init {
        executor.execute {
            val results = mutableSetOf<String>()
            val classes = repository.getAllClasses()
            for (csClass in classes) {
                results.add("CS ${csClass.id} - ${csClass.name}")
            }
            classNames = results.toList()

            val searchResults = repository.searchClasses("1400")
        }
    }
}