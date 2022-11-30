package com.cpp.cppcsclassscheduler.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cpp.cppcsclassscheduler.CsClass

@Database(entities = [CsClassFTS::class , CsClass::class], version = 2)
abstract class CsClassDatabase : RoomDatabase() {
    abstract fun CsClassDao(): CsClassDao
}