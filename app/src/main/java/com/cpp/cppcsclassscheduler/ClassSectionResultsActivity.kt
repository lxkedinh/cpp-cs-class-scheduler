package com.cpp.cppcsclassscheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
            val sections = viewmodel.csClassRepository.getAllSections(courseId)

            // populate recycler view with fetched sections back on main thread
            withContext(Dispatchers.Main) {
                val recyclerView: RecyclerView = findViewById(R.id.course_sections_recyclerview)
                recyclerView.adapter = CourseSectionAdapter(sections, R.layout.course_section_selection_list_item)
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            }
        }

        val shoppingCartButton: Button = findViewById(R.id.shopping_cart_button)
        shoppingCartButton.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }
    }

    private inner class sectionSelectionItemHolder(view: View): CourseSectionHolder(view) {

        val checkBox: CheckBox = itemView.findViewById(R.id.check_box)

        override fun bind(section: CsClass) {
            super.bind(section)

            checkBox.setOnClickListener { view ->
                if (view is CheckBox) {
                    if (view.isChecked) {
                        // add class to shopping cart
                        viewmodel.viewModelScope.launch(Dispatchers.IO) {
                            Log.d(TAG, "added class to cart")
                            viewmodel.cartRepository.addClassToCart(section)
                        }
                    } else {
                        // remove class from shopping cart
                        viewmodel.viewModelScope.launch(Dispatchers.IO) {
                            Log.d(TAG, "removed class from cart")
                            viewmodel.cartRepository.deleteClassFromCart(section)
                        }
                    }
                }
            }
        }
    }
}