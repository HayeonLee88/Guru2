package com.android.firebaseauth.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.firebaseauth.R
import com.android.firebaseauth.views.swu_in.*
import com.android.firebaseauth.views.swu_in.UserInputResult.UserInputResultActivity

class UserInputActivity : AppCompatActivity() {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    lateinit var btnResigter: Button

    lateinit var rgCategory: RadioGroup
    lateinit var rbtnFood: RadioButton
    lateinit var rbtnCafe: RadioButton
    lateinit var rbtnPhoto: RadioButton
    lateinit var rbtnRest: RadioButton
    lateinit var rbtnStudy: RadioButton

    lateinit var etPlace: EditText
    lateinit var etAddress: EditText
    lateinit var etExplain: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)

        btnResigter = findViewById(R.id.btnRegister)

        rgCategory = findViewById(R.id.rgCategory)

        rbtnFood = findViewById(R.id.rbtnFood)
        rbtnCafe = findViewById(R.id.rbtnCafe)
        rbtnPhoto = findViewById(R.id.rbtnPhoto)
        rbtnStudy = findViewById(R.id.rbtnStudy)
        rbtnRest = findViewById(R.id.rbtnRest)

        etPlace = findViewById(R.id.etPlace)
        etAddress = findViewById(R.id.etAddress)
        etExplain = findViewById(R.id.etExplain)

        dbManager = DBmanager(this, "PlaceDB", null, 1)

        btnResigter.setOnClickListener {

            var etPlacevalue: String? = etPlace.text.toString()
            var etAddressvalue: String? = etAddress.text.toString()
            var etExplainvalue: String? = etExplain.text.toString()

            var Categoryvalue: String? = ""

            if (rgCategory.checkedRadioButtonId == R.id.rbtnFood) {
                Categoryvalue = rbtnFood.text.toString()
            }
            if (rgCategory.checkedRadioButtonId == R.id.rbtnCafe) {
                Categoryvalue = rbtnCafe.text.toString()
            }
            if (rgCategory.checkedRadioButtonId == R.id.rbtnRest) {
                Categoryvalue = rbtnRest.text.toString()
            }
            if (rgCategory.checkedRadioButtonId == R.id.rbtnPhoto) {
                Categoryvalue = rbtnPhoto.text.toString()
            }
            if (rgCategory.checkedRadioButtonId == R.id.rbtnStudy) {
                Categoryvalue = rbtnStudy.text.toString()
            }

            sqllitedb = dbManager.writableDatabase
            sqllitedb.execSQL("INSERT INTO Place VALUES ('" + Categoryvalue + "','" + etPlacevalue + "', '" + etAddressvalue + "', ' " + etExplainvalue + "')")
            sqllitedb.close()

            if(Categoryvalue == rbtnFood.text.toString()){
                var intent = Intent(this, In_FoodMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if(Categoryvalue == rbtnCafe.text.toString()){
                var intent = Intent(this, In_CafeMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if(Categoryvalue == rbtnPhoto.text.toString()){
                var intent = Intent(this, In_PhotoMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if(Categoryvalue == rbtnStudy.text.toString()){
                var intent = Intent(this, In_StudyMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if(Categoryvalue == rbtnRest.text.toString()){
                var intent = Intent(this, In_RestMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }

            finish()

        }
    }
}