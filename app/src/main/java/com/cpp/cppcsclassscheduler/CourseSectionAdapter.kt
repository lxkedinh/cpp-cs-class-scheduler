package com.cpp.cppcsclassscheduler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseSectionAdapter(val sections: List<CsClass>, val inflatableViewResource: Int) :
    RecyclerView.Adapter<CourseSectionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseSectionHolder {
        val view = LayoutInflater.from(parent.context).inflate(inflatableViewResource, parent, false)
        return CourseSectionHolder(view)
    }

    override fun getItemCount() = sections.size

    override fun onBindViewHolder(holder: CourseSectionHolder, position: Int) {
        holder.bind(sections[position])
    }
}

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
