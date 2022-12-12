package com.cpp.cppcsclassscheduler

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "ClassSectionResultsActivity"

class ClassSectionResultsActivity : AppCompatActivity() {

    private val viewmodel: ClassSectionResultsViewModel by lazy {
        ViewModelProvider(this)[ClassSectionResultsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.class_section_list)
        val courseId = intent.getIntExtra(EXTRA_COURSE_ID, 0)
        val courseName = intent.getStringExtra(EXTRA_COURSE_NAME)

        val screenHeading = findViewById<TextView>(R.id.course_name)
        screenHeading.text = courseName

        viewmodel.viewModelScope.launch(Dispatchers.IO) {

            // fetch course sections on a separate IO thread
            val sections = viewmodel.repository.getAllSections(courseId)

            // populate recycler view with fetched sections back on main thread
            withContext(Dispatchers.Main) {
                val recyclerView: RecyclerView = findViewById(R.id.course_sections_recyclerview)
                val adapter = CourseSectionAdapter(sections)
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
                recyclerView.adapter = adapter
            }
        }
    }

    private inner class CourseSectionHolder(view: View): RecyclerView.ViewHolder(view) {

        val sectionNumberView: TextView = itemView.findViewById(R.id.course_section_number)
        val instructorView: TextView = itemView.findViewById(R.id.course_instructor)
        val roomView: TextView = itemView.findViewById(R.id.course_room)
        val daysView: TextView = itemView.findViewById(R.id.course_days)

        fun bind(section: CsClass) {
            sectionNumberView.text = getString(R.string.course_section_number, section.section)
            instructorView.text = getString(R.string.course_instructor, section.instructor)

            // check for TBA on section room or meeting days and times
            val roomText: String = if (section.room == "TBA") {
                "TBA"
            } else {
                val (building, room) = section.room.split(".")
                getString(R.string.course_room, building, room)
            }
            roomView.text = roomText

            val daysText: String =
                if (section.days == "TBA" ||
                    section.startTime == "TBA" ||
                    section.endTime == "TBA") {
                "TBA"
            } else {
                    getString(R.string.course_days, section.days, section.startTime, section.endTime)
            }
            daysView.text = daysText
        }
    }

    private inner class CourseSectionAdapter(var sections: List<CsClass>)
        : RecyclerView.Adapter<CourseSectionHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseSectionHolder {
            val view = layoutInflater.inflate(R.layout.course_section_item, parent, false)
            return CourseSectionHolder(view)
        }

        override fun getItemCount() = sections.size

        override fun onBindViewHolder(holder: CourseSectionHolder, position: Int) {
            holder.bind(sections[position])
        }
    }
}