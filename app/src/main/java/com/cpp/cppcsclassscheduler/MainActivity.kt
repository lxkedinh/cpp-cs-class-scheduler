package com.cpp.cppcsclassscheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = Gson()

        val inputStream = assets.open("data.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val classes = gson.fromJson(json, Array<CsClass>::class.java).toList()

    }
}