package com.cpp.cppcsclassscheduler.database

import androidx.room.Entity
import androidx.room.Fts4
import com.cpp.cppcsclassscheduler.CsClass

@Entity(tableName =  "csClasses_fts")
@Fts4(contentEntity = CsClass::class)
data class CsClassFTS(
    val name: String,
    val id: Int
)