package com.cpp.cppcsclassscheduler.activities.course_selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cpp.cppcsclassscheduler.R
import com.cpp.cppcsclassscheduler.activities.section_selection.SectionSelectionActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "CourseSearchActivity"
const val EXTRA_COURSE_ID = "com.cpp.cppcsclassscheduler.EXTRA_COURSE_ID"
const val EXTRA_COURSE_NAME = "com.cpp.cppcssclassscheduler.EXTRA_COURSE_NAME"

class CourseSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_search_results)

        val viewmodel: CourseSelectionViewModel by lazy {
            ViewModelProvider(this)[CourseSelectionViewModel::class.java]
        }

        // fetch all CPP cs classes on different thread in coroutine
        viewmodel.viewModelScope.launch(Dispatchers.IO) {
            val courseNames = viewmodel.getAllCourseNames()

            // populate recyclerview on main UI thread
            withContext(Dispatchers.Main) {
                val recyclerView: RecyclerView = findViewById(R.id.search_results_recyclerview)
                recyclerView.adapter = CourseAdapter(courseNames)
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            }
        }
    }

    private inner class CourseHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val courseNameTextView: TextView = itemView.findViewById(R.id.course_name_result)
        var courseId: Int = 0

        fun bind(course: Pair<String, Int>) {
            courseNameTextView.text = course.first
            courseId = course.second

            courseNameTextView.setOnClickListener {
                startActivity(courseSectionsIntent(
                    it.context,
                    courseId,
                    courseNameTextView.text as String
                ))
            }
        }
    }

    private inner class CourseAdapter(var courses: List<Pair<String,Int>>)
        : RecyclerView.Adapter<CourseHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
            val view = layoutInflater.inflate(R.layout.course_search_item, parent, false)
            return CourseHolder(view)
        }

        override fun getItemCount() = courses.size

        override fun onBindViewHolder(holder: CourseHolder, position: Int) {
            holder.bind(courses[position])
        }

    }

    companion object {
        fun courseSectionsIntent(packageContext: Context, courseId: Int, courseName: String): Intent {
            return Intent(packageContext, SectionSelectionActivity::class.java).apply {
                putExtra(EXTRA_COURSE_ID, courseId)
                putExtra(EXTRA_COURSE_NAME, courseName)
            }
        }
    }
}