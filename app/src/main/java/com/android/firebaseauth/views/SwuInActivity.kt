package com.android.firebaseauth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.android.firebaseauth.R
import com.android.firebaseauth.views.swu_in.*

class SwuInActivity() : AppCompatActivity() {
    lateinit var inTextView: TextView
    lateinit var catimageView: ImageView
    lateinit var btnFood: Button //음식점
    lateinit var btnCafe: Button //카페
    lateinit var btnRest: Button //휴식 스팟
    lateinit var btnPhoto: Button //사진 스팟
    lateinit var btnStudy: Button //스터디 스팟

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swuin)

        var actionBar : ActionBar
        actionBar = supportActionBar!!
        actionBar?.hide()

        btnFood = findViewById(R.id.btnFood)
        btnCafe = findViewById(R.id.btnCafe)
        btnPhoto = findViewById(R.id.btnPhoto)
        btnRest = findViewById(R.id.btnRest)
        btnStudy = findViewById(R.id.btnStudy)

        btnFood.setOnClickListener {
            val intent = Intent(this, In_FoodMapFragmentActivity::class.java) // 음식점 리스트 화면으로 전환
            startActivity(intent)
        }
        btnCafe.setOnClickListener {
            val intent = Intent(this, In_CafeMapFragmentActivity::class.java) // 카페 리스트 화면으로 전환
            startActivity(intent)
        }
        btnRest.setOnClickListener {
            val intent = Intent(this, In_RestMapFragmentActivity::class.java) // 쉴 곳 리스트 화면으로 전환
            startActivity(intent)
        }
        btnPhoto.setOnClickListener {
            val intent = Intent(this, In_PhotoMapFragmentActivity::class.java) // 사진 스팟 리스트 화면으로 전환
            startActivity(intent)
        }
        btnStudy.setOnClickListener {
            val intent = Intent(this, In_StudyMapFragmentActivity::class.java) // 스터디 스팟 리스트 화면으로 전환
            startActivity(intent)
        }
    }

}

