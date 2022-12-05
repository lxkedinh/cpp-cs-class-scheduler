package com.cpp.cppcsclassscheduler

import android.app.SearchManager
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

private const val TAG = "ClassSearchActivity"

class CourseSearchActivity : AppCompatActivity() {

    private val viewModel: CourseSearchViewModel by lazy {
        ViewModelProvider(this)[CourseSearchViewModel::class.java]
    }
    private var adapter: CourseAdapter? = CourseAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_search_results)

        // observe cs class live data and update search results
        val recyclerView = findViewById<RecyclerView>(R.id.search_results_recyclerview)
        val courseNameObserver = Observer<List<String>> { newCourses ->
            adapter = CourseAdapter(newCourses)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }
        viewModel.courseNames.observe(this, courseNameObserver)

        // TODO: verify the action and get search query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
            }
        }
    }

    private inner class CourseHolder(view: View)
        : RecyclerView.ViewHolder(view) {

        val courseNameTextView: TextView = itemView.findViewById(R.id.course_title)

        fun bind(course: String) {
            courseNameTextView.text = course
        }
    }

    private inner class CourseAdapter(var courses: List<String>)
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
}