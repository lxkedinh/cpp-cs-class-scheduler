package com.cpp.cppcsclassscheduler

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "csClasses")
data class CsClass(
    val courseId: Int,
    val name: String,
    val section: Int,
    val room: String,
    val instructor: String,
    val days: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "end_date") val endDate: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val rowId: Int? = null
)

//// virtual table to support full-text search
//@Entity(tableName = "csClasses_fts")
//@Fts4(contentEntity = CsClass::class)
//data class CsClassesFTS(
//    @ColumnInfo(name = "id")
//    val id: Int,
//    @ColumnInfo(name = "name")
//    val name: String
//)
