package com.cpp.cppcsclassscheduler.activities.shopping_cart

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.cpp.cppcsclassscheduler.R

const val OPEN_GOOGLE_CALENDAR_DIALOG_TAG = "Open Google Calendar Dialog"

class OpenGoogleCalendarDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.open_google_calendar_dialog_text)
                .setPositiveButton(
                    R.string.open_dialog_button_text
                ) { dialog, id ->
                    // open Google Calendar in user's web browser
                    val webpage = Uri.parse("https://calendar.google.com")
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    startActivity(intent)
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