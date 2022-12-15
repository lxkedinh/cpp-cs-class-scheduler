package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cpp.cppcsclassscheduler.R


class AddClassDialogFragment : DialogFragment() {

    val viewmodel: ShoppingCartViewModel by lazy {
        ViewModelProvider(this)[ShoppingCartViewModel::class.java]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.shopping_cart_add_classes_dialog_text)
                .setPositiveButton(R.string.shopping_cart_add_dialog_button_text,
                    DialogInterface.OnClickListener { dialog, id ->
                        // TODO: Google Calendar API calling code goes here
                        dismiss()
                    }
                )
                .setNegativeButton(R.string.shopping_cart_cancel_dialog_button_text,
                    DialogInterface.OnClickListener { dialog, id ->
                        dismiss()
                    }
                )
                .create()
        }
    }
}