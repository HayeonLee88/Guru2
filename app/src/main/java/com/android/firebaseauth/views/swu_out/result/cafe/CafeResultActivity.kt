package com.android.firebaseauth.views.swu_out.result.cafe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.android.firebaseauth.R

class CafeResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe_result)

        var actionBar : ActionBar
        actionBar = supportActionBar!!
        actionBar?.hide()
    }
}