package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cpp.cppcsclassscheduler.R
import com.cpp.cppcsclassscheduler.database.CsClass

// dialog fragment that pops up to confirm if user really wants to delete a class from their shopping cart
class DeleteSectionDialogFragment(val section: CsClass) : DialogFragment() {

    private val viewmodel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this)[ShoppingCartViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sectionText = "section ${section.section} of CS ${section.courseId} - ${section.name}"

        return activity.let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.shopping_cart_delete_confirmation_dialog, sectionText))
                .setPositiveButton(
                    R.string.delete_dialog_button_text
                ) { dialog, id ->
                    viewmodel.deleteCourseFromCart(section)
                    dismiss()
                }
                .setNegativeButton(
                    R.string.cancel_dialog_button_text
                ) { dialog, id ->
                    dismiss()
                }
                .create()
        }
    }
}