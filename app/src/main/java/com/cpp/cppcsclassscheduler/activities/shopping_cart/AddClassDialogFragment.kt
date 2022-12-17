package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cpp.cppcsclassscheduler.R

private const val TAG = "AddClassDialogFragment"

class AddClassDialogFragment : DialogFragment() {

    private val viewmodel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this)[ShoppingCartViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.shopping_cart_add_classes_dialog_text)
                .setPositiveButton(R.string.add_dialog_button_text
                ) { dialog, id ->
                    viewmodel.addCartToCalendar()

                    // give user option to open their google calendar with alert dialog
                    OpenGoogleCalendarDialogFragment().show(it!!.supportFragmentManager, OPEN_GOOGLE_CALENDAR_DIALOG_TAG)
                    dismiss()
                }
                .setNegativeButton(R.string.cancel_dialog_button_text
                ) { dialog, id ->
                    dismiss()
                }
                .create()
        }
    }
}