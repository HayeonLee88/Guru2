package com.android.firebaseauth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.firebaseauth.R

class ThemaActivity: AppCompatActivity() {
        lateinit var btnIn: Button //서울여대 내부
        lateinit var btnOut: Button // 서울여대 주변

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_thema)
            btnIn=findViewById(R.id.btnIn)
            btnOut=findViewById(R.id.btnOut)

            btnIn.setOnClickListener {
                val intent = Intent(this,SwuInActivity::class.java) // 서울여대 내부
                startActivity(intent)
            }
            btnOut.setOnClickListener {
                val intent = Intent(this,SwuOutActivity::class.java) // 서울여대 주변
                startActivity(intent)
            }
        }
    }
