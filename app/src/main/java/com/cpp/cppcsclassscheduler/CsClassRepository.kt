package com.cpp.cppcsclassscheduler

import android.content.Context
import androidx.room.Room
import com.cpp.cppcsclassscheduler.database.CsClassDatabase
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "cpp-cs-classes.db"

class CsClassRepository private constructor(context: Context) {

    private val database: CsClassDatabase = Room.databaseBuilder(
        context.applicationContext,
        CsClassDatabase::class.java,
        DATABASE_NAME
    )
        .createFromAsset("databases/cpp-cs-classes.db")
        .build()
    private val csClassDao = database.CsClassDao()

    suspend fun getAllClasses(): List<CsClass> = csClassDao.getAllClasses()

    fun getAllSections(id: Int): Flow<List<CsClass>> = csClassDao.getAllSections(id)

//    suspend fun addClasses(classes: List<CsClass>) = csClassDao.addClasses(classes)

//    fun searchClasses(query: String?): List<CsClass> = csClassDao.searchClasses(query)

    companion object {
        private var INSTANCE: CsClassRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CsClassRepository(context)
            }
        }

        fun get(): CsClassRepository {
            return INSTANCE ?:
            throw IllegalStateException("Cs Class Repository must be initialized")
        }
    }
}