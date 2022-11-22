package com.cpp.cppcsclassscheduler.database

import androidx.room.Dao
import androidx.room.Query
import com.cpp.cppcsclassscheduler.CsClass

@Dao
interface CsClassDao {
    @Query("SELECT * FROM csclass")
    fun getAll(): List<CsClass>

    @Query("SELECT * FROM csclass WHERE id = :id")
    fun getAllSections(id: Int): List<CsClass>

}