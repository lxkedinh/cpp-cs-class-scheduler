package com.cpp.cppcsclassscheduler.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cpp.cppcsclassscheduler.CsClass

@Dao
interface CsClassDao {
    @Query("SELECT * FROM csclass")
    fun getAllClasses(): List<CsClass>

    @Query("SELECT * FROM csclass WHERE id = :id")
    fun getAllSections(id: Int): List<CsClass>

    @Query("""
        SELECT *
        FROM csClasses_fts
        WHERE csClasses_fts MATCH :query
    """)
    fun searchClasses(query: String?): List<CsClassFTS>

    @Insert
    fun addClasses(classes: List<CsClass>)
}