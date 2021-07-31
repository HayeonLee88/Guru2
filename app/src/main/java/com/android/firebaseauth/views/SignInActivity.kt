package com.android.firebaseauth.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.firebaseauth.R
import android.content.Intent
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.actionCodeSettings
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInInputsArray = arrayOf(etSignInEmail, etSignInPassword)
        btnCreateAccount2.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        btnResetPassword.setOnClickListener {
            startActivity(Intent(this, ResetPwdActivity::class.java))
            finish()
        }

        btnSignIn.setOnClickListener {
            signInUser()
        }

    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser() {
        signInEmail = etSignInEmail.text.toString().trim() + "@swu.ac.kr"
        signInPassword = etSignInPassword.text.toString().trim()

        if (notEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        //startActivity(Intent(this, HomeActivity::class.java))
                        //Toast.makeText(this, "signed in successfully", Toast.LENGTH_SHORT).show()
                        //finish()
                        var auth: FirebaseAuth
                        auth = FirebaseAuth.getInstance()
                        val user: FirebaseUser?
                        user = auth.currentUser

                        if(user?.isEmailVerified == true){
                            startActivity(Intent(this, HomeActivity::class.java))
                            Toast.makeText(this, "로그인되었습니다", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, "이메일 인증을 완료해주세요", Toast.LENGTH_SHORT).show()
                            sendEmailVerification()
                            FirebaseAuth.getInstance().signOut()
                        }
                    } else {
                        Toast.makeText(this, signInEmail, Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} 을(를) 입력해주세요"
                }
            }
        }
    }

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
                    Toast.makeText(this, " $signInEmail 에 이메일을 보냈습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}