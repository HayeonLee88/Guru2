package com.android.firebaseauth.views

import android.content.Intent
import android.opengl.ETC1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.ControlsProviderService
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.android.firebaseauth.R
import com.google.firebase.auth.FirebaseAuth

class ResetPwdActivity : AppCompatActivity() {

    lateinit var etResetPWDEmail: EditText
    lateinit var btnResetPassword: Button
    lateinit var btnSignIn: Button

    lateinit var signInEmail: String
    lateinit var ResetpwdInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        var actionBar : ActionBar

        actionBar = supportActionBar!!
        actionBar?.hide()

        etResetPWDEmail = findViewById(R.id.etResetPWDEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword2)
        btnSignIn =  findViewById(R.id.btnSignIn3)

        btnResetPassword.setOnClickListener {
            findPassword()
        }

        btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "로그인 해주세요", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun findPassword() {
        signInEmail = etResetPWDEmail.text.toString().trim()
        FirebaseAuth.getInstance().sendPasswordResetEmail(signInEmail)
            .addOnCompleteListener {	task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "재설정 메일을 보냈습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Log.w(ControlsProviderService.TAG, "매일 재설정 : 실패", task.getException());
                    Toast.makeText(this, "재설정 실패", Toast.LENGTH_SHORT).show();
                }
            }
    }
}