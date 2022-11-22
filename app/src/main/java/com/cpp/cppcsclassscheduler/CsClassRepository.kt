package com.cpp.cppcsclassscheduler

import android.content.Context
import androidx.room.Room
import com.cpp.cppcsclassscheduler.database.CsClassDatabase
import java.util.concurrent.Executors

private const val DATABASE_NAME = "cpp-cs-classes"

class CsClassRepository private constructor(context: Context) {

    private val database : CsClassDatabase = Room.databaseBuilder(
        context.applicationContext,
        CsClassDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val csClassDao = database.CsClassDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllClasses(): List<CsClass> = csClassDao.getAllClasses()

    fun getSections(id: Int): List<CsClass>  {
        executor.execute {
            csClassDao.getAllSections(id)
        }
    }

    fun addClasses(classes: List<CsClass>) {
        executor.execute {
            csClassDao.addClasses(classes)
        }
    }

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