package com.cpp.cppcsclassscheduler.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cpp.cppcsclassscheduler.CsClass

@Database(entities = [CsClass::class], version = 1)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun cartDao(): ShoppingCartDao
}