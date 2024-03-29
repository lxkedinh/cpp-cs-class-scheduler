package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cpp.cppcsclassscheduler.CourseSectionHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.cpp.cppcsclassscheduler.database.CsClass
import com.cpp.cppcsclassscheduler.R

private const val CART_DELETE_DIALOG_TAG = "Cart Delete Dialog"
private const val ADD_CLASSES_DIALOG_TAG = "Add Classes Dialog"

class ShoppingCartActivity : AppCompatActivity() {

    private val viewmodel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this)[ShoppingCartViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.statusBarColor = getColor(R.color.blue)
        window.navigationBarColor = getColor(R.color.light_blue)
        setContentView(R.layout.shopping_cart_view)

        viewmodel.viewModelScope.launch(Dispatchers.IO) {
            // fetch shopping cart
            viewmodel.getShoppingCart().collect {
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
            // show add confirmation alert dialog
            AddClassDialogFragment().show(supportFragmentManager, ADD_CLASSES_DIALOG_TAG)
        }
    }

    private inner class SectionCartItemAdapter(val sections: List<CsClass>) :
        RecyclerView.Adapter<SectionCartItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionCartItemHolder {
            val view = layoutInflater.inflate(R.layout.course_section_cart_item, parent, false)
            return SectionCartItemHolder(view)
        }

        override fun getItemCount() = sections.size

        override fun onBindViewHolder(holder: SectionCartItemHolder, position: Int) {
            holder.bind(sections[position])
        }
    }

    private inner class SectionCartItemHolder(view: View): CourseSectionHolder(view) {

        val deleteButton: Button = itemView.findViewById(R.id.course_section_delete_button)

        override fun bind(section: CsClass) {
            super.bind(section)

            // show delete confirmation alert dialog
            deleteButton.setOnClickListener { DeleteSectionDialogFragment(section).show(supportFragmentManager, CART_DELETE_DIALOG_TAG) }
        }
    }
}