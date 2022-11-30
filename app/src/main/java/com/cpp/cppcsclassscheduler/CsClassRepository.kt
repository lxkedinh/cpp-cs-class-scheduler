package com.cpp.cppcsclassscheduler

import android.content.Context
import androidx.room.Room
import com.cpp.cppcsclassscheduler.database.CsClassDatabase

private const val DATABASE_NAME = "cpp-cs-classes"

class CsClassRepository private constructor(context: Context) {

    private val database : CsClassDatabase = Room.databaseBuilder(
        context.applicationContext,
        CsClassDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val csClassDao = database.CsClassDao()

    fun getAllClasses(): List<CsClass> = csClassDao.getAllClasses()

    fun getSections(id: Int): List<CsClass> = csClassDao.getAllSections(id)

    fun searchClasses(query: String?): List<CsClass> = csClassDao.searchClasses(query)

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