package com.cpp.cppcsclassscheduler.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cpp.cppcsclassscheduler.CsClass
import kotlinx.coroutines.flow.Flow

@Dao
interface CsClassDao {
    @Query("SELECT * FROM csClasses")
    suspend fun getAllClasses(): List<CsClass>

    @Query("SELECT * FROM csClasses WHERE courseId = :id")
    suspend fun getAllSections(id: Int): List<CsClass>

//    @Query("""
//        SELECT *
//        FROM csClasses_fts
//        WHERE csClasses_fts MATCH :query
//    """)
//    fun searchClasses(query: String?): List<CsClass>

    @Insert
    suspend fun addClasses(classes: List<CsClass>)
}