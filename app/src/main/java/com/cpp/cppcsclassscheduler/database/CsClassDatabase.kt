package com.cpp.cppcsclassscheduler.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cpp.cppcsclassscheduler.CsClass

@Database(entities = [CsClass::class], version = 3)
abstract class CsClassDatabase : RoomDatabase() {
    abstract fun CsClassDao(): CsClassDao
}