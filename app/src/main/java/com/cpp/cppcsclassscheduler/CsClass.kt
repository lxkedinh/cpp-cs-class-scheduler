package com.cpp.cppcsclassscheduler

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id", "section"])
data class CsClass(
    val id: Int,
    val name: String,
    val section: Int,
    val room: String,
    val instructor: String,
    val days: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "end_date") val endDate: String
)
