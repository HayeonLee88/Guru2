package com.android.firebaseauth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.firebaseauth.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeActivity : AppCompatActivity() {

    lateinit var btnSignOut: Button
    lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnSignOut = findViewById(R.id.btnSignOut)
        btnStart = findViewById(R.id.btnStart)

        var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser?
        user = auth.currentUser //현재 로그인 정보

        if(user==null){ //로그인 안함 -> 로그인 화면 전환
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        //로그아웃 버튼
        btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
            finish()
        }
        //시작 버튼
        btnStart.setOnClickListener {
            val intent = Intent(this, ThemaActivity::class.java) // 테마 버튼 화면으로 전환
            startActivity(intent)
            finish()
        }
    }
}