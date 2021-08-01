package com.android.firebaseauth.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.firebaseauth.R
import com.android.firebaseauth.views.swu_out.*


class SwuOutActivity : AppCompatActivity() {
    lateinit var outTextView: TextView
    lateinit var catiamgeView: ImageView
    lateinit var btnEat: Button //음식점
    lateinit var btnDrink: Button //카페
    lateinit var btnPlay: Button //놀거리
    lateinit var btnWalk: Button //산책 스팟
    lateinit var btnSstudy: Button //스터디 스팟

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swuout)

        btnEat = findViewById(R.id.btnEat)
        btnDrink = findViewById(R.id.btnDrink)
        btnPlay = findViewById(R.id.btnPlay)
        btnWalk = findViewById(R.id.btnWalk)
        btnSstudy = findViewById(R.id.btnSstudy)

        btnEat.setOnClickListener {
            val intent = Intent(this, FoodMapFragmentActivity::class.java) // 음식점 리스트 화면으로 전환
            startActivity(intent)
        }
        btnDrink.setOnClickListener {
            val intent = Intent(this, CafeMapFragmentActivity::class.java) // 카페 리스트 화면으로 전환
            startActivity(intent)
        }
        btnPlay.setOnClickListener {
            val intent = Intent(this, PlayMapFragmentActivity::class.java) // 놀 곳 리스트 화면으로 전환
            startActivity(intent)
        }
        btnWalk.setOnClickListener {
            val intent = Intent(this, WalkMapFragmentActivity::class.java) // 산책 스팟 리스트 화면으로 전환
            startActivity(intent)
        }
        btnSstudy.setOnClickListener {
            val intent = Intent(this, StudyMapFragmentActivity::class.java) // 스터디 스팟 리스트 화면으로 전환
            startActivity(intent)
        }
    }
}
