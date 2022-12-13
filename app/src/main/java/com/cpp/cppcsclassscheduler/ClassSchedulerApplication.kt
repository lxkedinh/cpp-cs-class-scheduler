package com.cpp.cppcsclassscheduler

import android.app.Application

class ClassSchedulerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CsClassRepository.initialize(this)
        ShoppingCartRepository.initialize(this)
    }
}