package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cpp.cppcsclassscheduler.R
import com.cpp.cppcsclassscheduler.calendar_api.CalendarClient
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events
import java.util.*


private const val TAG = "AddClassDialogFragment"

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
                        // TODO: Convert cs class objects into calendar events and then add to user's google calendar
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