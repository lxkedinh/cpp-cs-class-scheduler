package com.cpp.cppcsclassscheduler

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CourseSearchActivity"
const val EXTRA_COURSE_ID = "com.cpp.cppcsclassscheduler.EXTRA_COURSE_ID"
const val EXTRA_COURSE_NAME = "com.cpp.cppcssclassscheduler.EXTRA_COURSE_NAME"

class CourseSearchActivity : AppCompatActivity() {

    private val viewmodel: CourseSearchViewModel by lazy {
        ViewModelProvider(this)[CourseSearchViewModel::class.java]
    }
    private var adapter: CourseAdapter? = CourseAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_search_results)

        // observe cs class live data and update search results
        val recyclerView = findViewById<RecyclerView>(R.id.search_results_recyclerview)
        val courseNameObserver = Observer<List<Pair<String,Int>>> { newCourses ->
            adapter = CourseAdapter(newCourses)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
        viewmodel.courseNames.observe(this, courseNameObserver)

        // TODO: verify the action and get search query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
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
            return Intent(packageContext, ClassSectionResultsActivity::class.java).apply {
                putExtra(EXTRA_COURSE_ID, courseId)
                putExtra(EXTRA_COURSE_NAME, courseName)
            }
        }
    }
}