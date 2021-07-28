package com.android.firebaseauth.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.firebaseauth.R
import kotlinx.android.synthetic.main.activity_create_account.*
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.actionCodeSettings

class CreateAccountActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        createAccountInputsArray = arrayOf(etEmail, etPassword, etConfirmPassword)
        btnCreateAccount.setOnClickListener {
            signIn()
        }

        btnSignIn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "please sign into your account", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser?
        user = auth.currentUser
        user?.let {
            startActivity(Intent(this, HomeActivity::class.java))
            Toast.makeText(this, "welcome back", Toast.LENGTH_SHORT).show()
        }
    }

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty() &&
            etPassword.text.toString().trim().isNotEmpty() &&
            etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            etPassword.text.toString().trim() == etConfirmPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            Toast.makeText(this, "passwords are not matching !", Toast.LENGTH_SHORT).show()
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {

            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()

            /*create a user*/
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "created account successfully !",
                            Toast.LENGTH_SHORT
                        ).show()
                        sendEmailVerification()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                }
        } else {
            Toast.makeText(this, "failed to Authenticate !", Toast.LENGTH_SHORT).show()
        }
    }

/* send verification email to the new user. This will only
*  work if the firebase user is not null.
*/

    private fun sendEmailVerification() {

        val actionCodeSettings = actionCodeSettings {
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            url = "https://www.example.com/finishSignUp?cartId=1234"
            // This must be true
            handleCodeInApp = true
            setIOSBundleId("com.example.ios")
            setAndroidPackageName(
                "com.example.android",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */
            )
        }

        FirebaseAuth.getInstance().currentUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "email sent to $userEmail", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}