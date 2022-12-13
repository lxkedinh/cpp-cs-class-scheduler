package com.cpp.cppcsclassscheduler

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
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
        setContentView(R.layout.course_section_list)
        val courseId = intent.getIntExtra(EXTRA_COURSE_ID, 0)
        val courseName = intent.getStringExtra(EXTRA_COURSE_NAME)

        val screenHeading = findViewById<TextView>(R.id.course_name)
        screenHeading.text = courseName

        viewmodel.viewModelScope.launch(Dispatchers.IO) {
            // fetch course sections on a separate IO thread
            val sections = viewmodel.getAllSections(courseId)

            // populate recycler view with fetched sections back on main thread
            withContext(Dispatchers.Main) {
                val recyclerView: RecyclerView = findViewById(R.id.course_sections_recyclerview)
                recyclerView.adapter = SectionCheckboxItemAdapter(sections)
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            }
        }

        val shoppingCartButton: Button = findViewById(R.id.shopping_cart_button)
        shoppingCartButton.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

    private inner class SectionCheckboxItemAdapter(val sections: List<CsClass>) :
        RecyclerView.Adapter<SectionCheckboxItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionCheckboxItemHolder {
            val view = layoutInflater.inflate(R.layout.course_section_selection_list_item, parent, false)
            return SectionCheckboxItemHolder(view)
        }

        override fun getItemCount() = sections.size

        override fun onBindViewHolder(holder: SectionCheckboxItemHolder, position: Int) {
            holder.bind(sections[position])
        }
    }

    private inner class SectionCheckboxItemHolder(view: View): CourseSectionHolder(view) {

        val checkBox: CheckBox = itemView.findViewById(R.id.check_box)

        override fun bind(section: CsClass) {
            super.bind(section)

            viewmodel.viewModelScope.launch {
                val isClassInCart = withContext(Dispatchers.IO) {
                    viewmodel.queryClassInCart(section.courseId, section.section)
                }

                checkBox.isChecked = isClassInCart
            }

            checkBox.setOnClickListener { view ->
                if (view is CheckBox) {
                    if (view.isChecked) {
                        // add class to shopping cart if checkbox becomes checked
                        viewmodel.addClassToCart(section)
                    } else {
                        // remove class from shopping cart if user unchecks checkbox
                        viewmodel.deleteClassFromCart(section)
                    }
                }
            }
        }
    }
}