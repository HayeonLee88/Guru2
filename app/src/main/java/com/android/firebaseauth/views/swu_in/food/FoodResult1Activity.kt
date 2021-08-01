package com.example.guru_n.swu_in.food

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.android.firebaseauth.R


///////////서울여대 음식점 _ 아딸 떡볶이///////


class FoodResult1Activity : Activity(){

    private lateinit var view_get : TextView
    private lateinit var view_get2 : TextView
    private lateinit var view_get3 : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.in_food_result)

        //view_get = findViewById(R.id.view_get)
        //view_get2 = findViewById(R.id.view_get2)
        //view_get3 = findViewById(R.id.view_get3)

        val intent : Intent = intent
        val name : String? = intent.getStringExtra("name")
        val n1 : Double = intent.getDoubleExtra("lat", 0.0)
        val n2 : Double = intent.getDoubleExtra("lng", 0.0)

       // view_get.setText(name)
       // view_get2.setText(n1.toString())
       // view_get3.setText(n2.toString())

    }
}