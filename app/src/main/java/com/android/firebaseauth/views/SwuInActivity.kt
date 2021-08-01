package com.android.firebaseauth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.firebaseauth.R

class SwuInActivity : AppCompatActivity() {

    lateinit var inTextView: TextView
    lateinit var catimageView: ImageView
    lateinit var btnFood: Button //음식점
    lateinit var btnCafe: Button //카페
    lateinit var btnRest: Button //휴식 스팟
    lateinit var btnPhoto: Button //사진 스팟
    lateinit var btnStudy: Button //스터디 스팟

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swuin)

        btnFood = findViewById(R.id.btnFood)
        btnCafe = findViewById(R.id.btnCafe)
        btnPhoto = findViewById(R.id.btnPhoto)
        btnRest = findViewById(R.id.btnRest)
        btnStudy = findViewById(R.id.btnStudy)

        btnFood.setOnClickListener {
            val intent = Intent(this, SwuInActivity::class.java) // 음식점 리스트 화면으로 전환 (하진님이 여기 연결해주세요!!)
            startActivity(intent)
        }
        btnCafe.setOnClickListener {
            val intent = Intent(this, SwuOutActivity::class.java) // 카페 리스트 화면으로 전환
            startActivity(intent)
        }
        btnRest.setOnClickListener {
            val intent = Intent(this,SwuInActivity::class.java) // 쉴 곳 리스트 화면으로 전환
            startActivity(intent)
        }
        btnPhoto.setOnClickListener {
            val intent = Intent(this,SwuOutActivity::class.java) // 사진 스팟 리스트 화면으로 전환
            startActivity(intent)
        }
        btnStudy.setOnClickListener {
            val intent = Intent(this,SwuInActivity::class.java) // 스터디 스팟 리스트 화면으로 전환
            startActivity(intent)
        }
    }
}

