package com.cpp.cppcsclassscheduler

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShoppingCartActivity : AppCompatActivity() {

    private val viewmodel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this)[ShoppingCartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_cart_view)

        viewmodel.viewModelScope.launch(Dispatchers.IO) {
            // fetch shopping cart
            viewmodel.getShoppingCart().collect() {
                withContext(Dispatchers.Main) {
                    // populate recycler view in UI thread
                    val recyclerview: RecyclerView = findViewById(R.id.shopping_cart_recyclerview)
                    recyclerview.adapter = SectionCartItemAdapter(it)
                    recyclerview.layoutManager = LinearLayoutManager(recyclerview.context)
                }
            }
        }

        val addClassesButton: Button = findViewById(R.id.add_to_calendar_button)
        addClassesButton.setOnClickListener {
            viewmodel.addCartToCalendar()
        }
    }

    private inner class SectionCartItemAdapter(val sections: List<CsClass>) :
        RecyclerView.Adapter<SectionCartItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionCartItemHolder {
            val view = layoutInflater.inflate(R.layout.course_section_list_item, parent, false)
            return SectionCartItemHolder(view)
        }

        override fun getItemCount() = sections.size

        override fun onBindViewHolder(holder: SectionCartItemHolder, position: Int) {
            holder.bind(sections[position])
        }
    }

    private inner class SectionCartItemHolder(view: View): CourseSectionHolder(view) {

        override fun bind(section: CsClass) {
            super.bind(section)
        }
    }
}