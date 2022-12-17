package com.cpp.cppcsclassscheduler.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CsClassDao {
    @Query("SELECT * FROM csClasses")
    suspend fun getAllClasses(): List<CsClass>

    @Query("SELECT * FROM csClasses WHERE courseId = :id")
    suspend fun getAllSections(id: Int): List<CsClass>

    @Insert
    suspend fun addClasses(classes: List<CsClass>)
}