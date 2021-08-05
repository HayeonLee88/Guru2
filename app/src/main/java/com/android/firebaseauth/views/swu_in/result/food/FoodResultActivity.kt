package com.android.firebaseauth.views.swu_in.result.food

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.android.firebaseauth.R

class FoodResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_result)

        var actionBar : ActionBar
        actionBar = supportActionBar!!
        actionBar?.hide()
    }
}