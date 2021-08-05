package com.android.firebaseauth.views.swu_in.UserInputResult

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.android.firebaseauth.R
import com.android.firebaseauth.views.DBmanager
import java.util.*

class UserInputResultActivity : AppCompatActivity() {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input_result)

        var actionBar : ActionBar
        actionBar = supportActionBar!!
        actionBar?.hide()

        var intent = intent

        var ThemeValue: String? = intent.getStringExtra("theme")
        var CategoryValue: String? = intent.getStringExtra("category")
        var PlaceValue: String? = intent.getStringExtra("place")
        var AddressValue: String? = intent.getStringExtra("address")
        var ExplainValue: String? = intent.getStringExtra("explain")
        var tvPlace: TextView
        var tvAddress: TextView
        var tvExplain: TextView

        tvAddress = findViewById(R.id.addAddress)
        tvExplain = findViewById(R.id.addExplain)
        tvPlace = findViewById(R.id.Place)

        dbManager = DBmanager(this, "PlaceDB", null, 1)
        sqllitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor =
            sqllitedb.rawQuery("SELECT * FROM Place WHERE place = '" + PlaceValue + "';", null)

        if (cursor.moveToNext()) {
            ThemeValue = cursor.getString(cursor.getColumnIndex("theme")).toString()
            CategoryValue = cursor.getString(cursor.getColumnIndex("category")).toString()
            AddressValue = cursor.getString(cursor.getColumnIndex("address")).toString()
            ExplainValue = cursor.getString(cursor.getColumnIndex("detail")).toString()
        }

        cursor.close()
        sqllitedb.close()
        dbManager.close()

        tvPlace.text = "["+ThemeValue +" " +CategoryValue +"] " + PlaceValue
        tvAddress.text = AddressValue
        tvExplain.text = ExplainValue

    }
}