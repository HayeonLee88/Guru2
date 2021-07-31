package com.android.firebaseauth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.android.firebaseauth.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_pwd.*

class ResetPwdActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var ResetpwdInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        ResetpwdInputsArray = arrayOf(etResetPWDEmail)

        btnResetPassword2.setOnClickListener {
            findPassword()
        }

        btnSignIn3.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "please sign into your account", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun findPassword() {
        signInEmail = etResetPWDEmail.text.toString().trim()
        FirebaseAuth.getInstance().sendPasswordResetEmail(signInEmail)
            .addOnCompleteListener {	task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "재설정 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(ControlsProviderService.TAG, "매일 재설정 : 실패", task.getException());
                    Toast.makeText(this, "재설정 실패.", Toast.LENGTH_SHORT).show();
                }
            }
    }
}