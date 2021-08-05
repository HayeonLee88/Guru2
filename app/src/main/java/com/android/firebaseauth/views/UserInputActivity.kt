package com.android.firebaseauth.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.firebaseauth.R
import com.android.firebaseauth.views.swu_in.*
import com.android.firebaseauth.views.swu_in.UserInputResult.UserInputResultActivity
import com.android.firebaseauth.views.swu_out.*

class UserInputActivity : AppCompatActivity() {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    lateinit var btnResigter: Button

    lateinit var rgTheme: RadioGroup
    lateinit var rbtnIn: RadioButton
    lateinit var rbtnOut: RadioButton

    lateinit var rgInCategory: RadioGroup
    lateinit var rbtnFood1: RadioButton
    lateinit var rbtnCafe1: RadioButton
    lateinit var rbtnPhoto1: RadioButton
    lateinit var rbtnRest1: RadioButton
    lateinit var rbtnStudy1: RadioButton

    lateinit var rgOutCategory: RadioGroup
    lateinit var rbtnFood2: RadioButton
    lateinit var rbtnCafe2: RadioButton
    lateinit var rbtnWalk1: RadioButton
    lateinit var rbtnPlay1: RadioButton
    lateinit var rbtnStudy2: RadioButton

    lateinit var etPlace: EditText
    lateinit var etAddress: EditText
    lateinit var etExplain: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)

        btnResigter = findViewById(R.id.btnRegister)

        rgTheme = findViewById(R.id.rgtheme)

        rbtnIn = findViewById(R.id.rbtnIn)
        rbtnOut = findViewById(R.id.rbtnOut)

        rgInCategory = findViewById(R.id.rgInCategory)

        rbtnFood1 = findViewById(R.id.rbtnFood1)
        rbtnCafe1 = findViewById(R.id.rbtnCafe1)
        rbtnPhoto1 = findViewById(R.id.rbtnPhoto1)
        rbtnStudy1 = findViewById(R.id.rbtnStudy1)
        rbtnRest1 = findViewById(R.id.rbtnRest1)

        rgOutCategory = findViewById(R.id.rgOutCategory)

        rbtnFood2 = findViewById(R.id.rbtnFood2)
        rbtnCafe2 = findViewById(R.id.rbtnCafe2)
        rbtnWalk1 = findViewById(R.id.rbtnWalk1)
        rbtnStudy2 = findViewById(R.id.rbtnStudy2)
        rbtnPlay1 = findViewById(R.id.rbtnPlay1)

        etPlace = findViewById(R.id.etPlace)
        etAddress = findViewById(R.id.etAddress)
        etExplain = findViewById(R.id.etExplain)

        dbManager = DBmanager(this, "PlaceDB", null, 1)

        rgTheme.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rbtnIn -> {rgInCategory.setVisibility(View.VISIBLE)
                rgOutCategory.setVisibility(View.GONE)}
                R.id.rbtnOut -> {rgOutCategory.setVisibility(View.VISIBLE)
                                rgInCategory.setVisibility(View.GONE)}

            }
        }

        btnResigter.setOnClickListener {

            //서을여대, 서울여대 주변
            var Themevalue: String? = ""

            if (rgTheme.checkedRadioButtonId == R.id.rbtnOut) {
                Themevalue = rbtnOut.text.toString()
            }
            if (rgTheme.checkedRadioButtonId == R.id.rbtnIn) {
                Themevalue = rbtnIn.text.toString()
            }

            var etPlacevalue: String? = etPlace.text.toString()
            var etAddressvalue: String? = etAddress.text.toString()
            var etExplainvalue: String? = etExplain.text.toString()

            var Categoryvalue: String? = ""

            //서울여대 내부
            if (rgInCategory.checkedRadioButtonId == R.id.rbtnFood1) {
                Categoryvalue = rbtnFood1.text.toString()
            }
            if (rgInCategory.checkedRadioButtonId == R.id.rbtnCafe1) {
                Categoryvalue = rbtnCafe1.text.toString()
            }
            if (rgInCategory.checkedRadioButtonId == R.id.rbtnRest1) {
                Categoryvalue = rbtnRest1.text.toString()
            }
            if (rgInCategory.checkedRadioButtonId == R.id.rbtnPhoto1) {
                Categoryvalue = rbtnPhoto1.text.toString()
            }
            if (rgInCategory.checkedRadioButtonId == R.id.rbtnStudy1) {
                Categoryvalue = rbtnStudy1.text.toString()
            }


            //서울여대 주변
            if (rgOutCategory.checkedRadioButtonId == R.id.rbtnFood2) {
                Categoryvalue = rbtnFood2.text.toString()
            }
            if (rgOutCategory.checkedRadioButtonId == R.id.rbtnCafe2) {
                Categoryvalue = rbtnCafe2.text.toString()
            }
            if (rgOutCategory.checkedRadioButtonId == R.id.rbtnPlay1) {
                Categoryvalue = rbtnPlay1.text.toString()
            }
            if (rgOutCategory.checkedRadioButtonId == R.id.rbtnWalk1) {
                Categoryvalue = rbtnWalk1.text.toString()
            }
            if (rgOutCategory.checkedRadioButtonId == R.id.rbtnStudy2) {
                Categoryvalue = rbtnStudy2.text.toString()
            }

            sqllitedb = dbManager.writableDatabase
            sqllitedb.execSQL("INSERT INTO Place VALUES ('" + Themevalue + "','" + Categoryvalue + "','" + etPlacevalue + "', '" + etAddressvalue + "', ' " + etExplainvalue + "')")
            sqllitedb.close()

            //서울여대 내부
            if (Themevalue == rbtnIn.text.toString() && Categoryvalue == rbtnFood1.text.toString()) {
                var intent = Intent(this, In_FoodMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Themevalue == rbtnIn.text.toString() && Categoryvalue == rbtnCafe1.text.toString()) {
                var intent = Intent(this, In_CafeMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Categoryvalue == rbtnPhoto1.text.toString()) {
                var intent = Intent(this, In_PhotoMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Themevalue == rbtnIn.text.toString() && Categoryvalue == rbtnStudy1.text.toString()) {
                var intent = Intent(this, In_StudyMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Categoryvalue == rbtnRest1.text.toString()) {
                var intent = Intent(this, In_RestMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }

            //서울여대 주변
            if ( Themevalue == rbtnOut.text.toString() && Categoryvalue == rbtnFood2.text.toString()) {
                var intent = Intent(this, FoodMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Themevalue == rbtnOut.text.toString() &&  Categoryvalue == rbtnCafe2.text.toString()) {
                var intent = Intent(this, CafeMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Categoryvalue == rbtnWalk1.text.toString()) {
                var intent = Intent(this, WalkMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if ( Themevalue == rbtnOut.text.toString() && Categoryvalue == rbtnStudy2.text.toString()) {
                var intent = Intent(this, StudyMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }
            if (Categoryvalue == rbtnPlay1.text.toString()) {
                var intent = Intent(this, PlayMapFragmentActivity::class.java)
                intent.putExtra("place", etPlacevalue)
                intent.putExtra("buttonVisible", true)

                startActivity(intent)
            }

            finish()

        }
    }

    inner class RadioListener : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: RadioGroup?, p1: Int) { // p1 사용자가 선택한 라디오 버튼의 아이디값
            when (p0?.id) {
                R.id.rgtheme ->
                    when (p1) {
                        R.id.rbtnIn -> rgInCategory.setVisibility(View.VISIBLE)

                        R.id.rbtnOut -> rgOutCategory.setVisibility(View.VISIBLE)
                    }
            }
        }
    }
}