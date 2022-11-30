package com.cpp.cppcsclassscheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.util.concurrent.Executors

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val repository = CsClassRepository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val skipButton = findViewById<Button>(R.id.button2)
        skipButton.setOnClickListener {
            val intent = Intent(this, ClassSearchActivity::class.java)
            startActivity(intent)
        }

        val executor = Executors.newSingleThreadExecutor()
        var classes: List<CsClass>
        executor.execute {
            classes = repository.searchClasses("python")
            Log.d(TAG, classes.size.toString())
            for (csClass in classes) {
                Log.d(TAG, csClass.name)
            }
        }


    }
}