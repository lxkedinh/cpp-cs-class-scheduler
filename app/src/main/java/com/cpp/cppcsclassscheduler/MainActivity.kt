package com.cpp.cppcsclassscheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val skipButton = findViewById<Button>(R.id.button2)
        skipButton.setOnClickListener {
            val intent = Intent(this, ClassListView::class.java)
            startActivity(intent)
        }
    }
}