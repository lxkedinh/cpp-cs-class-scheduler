package com.cpp.cppcsclassscheduler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cpp.cppcsclassscheduler.database.CsClass

// base course section holder class that is open so I can extend it with listeners
// for future view holders like the shopping cart recyclerview
open class CourseSectionHolder(view: View): RecyclerView.ViewHolder(view) {

    val sectionNumberView: TextView = itemView.findViewById(R.id.course_section_number)
    val instructorView: TextView = itemView.findViewById(R.id.course_instructor)
    val roomView: TextView = itemView.findViewById(R.id.course_room)
    val daysView: TextView = itemView.findViewById(R.id.course_days)

    open fun bind(section: CsClass) {
        sectionNumberView.text = itemView.context.getString(R.string.course_section_number, section.section)
        instructorView.text = itemView.context.getString(R.string.course_instructor, section.instructor)

        // check for TBA on section room or meeting days and times
        val roomText: String = if (section.room == "TBA") {
            "TBA"
        } else {
            val (building, room) = section.room.split(".")
            itemView.context.getString(R.string.course_room, building, room)
        }
        roomView.text = roomText

        val daysText: String =
            if (section.days == "TBA" ||
                section.startTime == "TBA" ||
                section.endTime == "TBA") {
                "TBA"
            } else {
                itemView.context.getString(R.string.course_days, section.days, section.startTime, section.endTime)
            }
        daysView.text = daysText
    }
}
