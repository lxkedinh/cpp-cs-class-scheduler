package com.cpp.cppcsclassscheduler.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cpp.cppcsclassscheduler.R
import com.cpp.cppcsclassscheduler.activities.course_selection.CourseSelectionActivity
import com.cpp.cppcsclassscheduler.calendar_api.CalendarClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.calendar.CalendarScopes


private const val TAG = "MainActivity"
private const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(CalendarScopes.CALENDAR_EVENTS))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account != null){
            // initialize Google Calendar client with account credentials
            val credential = GoogleAccountCredential.usingOAuth2(this, CalendarClient.CALENDAR_SCOPES)
            credential.selectedAccount = account.account
            CalendarClient.initialize(applicationContext, credential)

            val intent = Intent(this, CourseSelectionActivity::class.java)
            startActivity(intent)
        }

        val signInButton = findViewById<Button>(R.id.login_button)
        signInButton.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // initialize Google Calendar client with account credentials
            val credential = GoogleAccountCredential.usingOAuth2(this, CalendarClient.CALENDAR_SCOPES)
            credential.selectedAccount = account.account
            CalendarClient.initialize(applicationContext, credential)

            // Signed in successfully, show authenticated UI.
            val intent = Intent(this, CourseSelectionActivity::class.java)
            startActivity(intent)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}