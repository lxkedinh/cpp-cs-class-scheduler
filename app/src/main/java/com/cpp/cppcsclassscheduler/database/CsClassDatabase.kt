package com.cpp.cppcsclassscheduler.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CsClass::class], version = 1)
abstract class CsClassDatabase : RoomDatabase() {
    abstract fun CsClassDao(): CsClassDao
}