package com.cpp.cppcsclassscheduler

import android.app.Application
import com.cpp.cppcsclassscheduler.database.CsClassRepository
import com.cpp.cppcsclassscheduler.database.ShoppingCartRepository

class ClassSchedulerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CsClassRepository.initialize(this)
        ShoppingCartRepository.initialize(this)
    }
}


