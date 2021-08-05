package com.android.firebaseauth.views.swu_out

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.NonNull
import com.android.firebaseauth.R
import com.android.firebaseauth.views.DBmanager
import com.android.firebaseauth.views.swu_in.UserInputResult.UserInputResultActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class PlayMapFragmentActivity : Activity(), OnMapReadyCallback {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    // 버튼 변수
    private lateinit var PlaybtnList4: Button


    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE : Int = 1000;
    private lateinit var locationSource : FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()
    private val marker3 = Marker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.out_playfragment_map)

        PlaybtnList4 = findViewById(R.id.PlaybtnList4)

        mapView = findViewById(R.id.Playmap_view)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        var intent = intent
        var PlaceValue: String? = intent.getStringExtra("place")

        dbManager = DBmanager(this, "PlaceDB", null, 1)
        sqllitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor =
            sqllitedb.rawQuery("SELECT * FROM Place where category = '놀거리';", null)

        if (cursor.moveToNext()) {
            PlaybtnList4.setVisibility(View.VISIBLE)
            PlaceValue = cursor.getString(cursor.getColumnIndex("place")).toString()
        }
        PlaybtnList4.text = PlaceValue

        PlaybtnList4.setOnClickListener {
            var intent = Intent(this, UserInputResultActivity::class.java)
            intent.putExtra("place", PlaceValue)
            startActivity(intent)
        }

    }


    override fun onMapReady(@NonNull naverMap:NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // 현재 위치 마커
        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED

        val cameraPosition = CameraPosition(
                LatLng(37.6281, 127.0905),  // 위치 지정
                13.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition

        // 장소 리스트 마커
        marker1.position = LatLng(37.624560825872, 127.07576058445098)
        marker1.map = naverMap
        marker1.captionText = "피치플레이버샵"
        marker2.position = LatLng(37.62890622081774, 127.07679002863425)
        marker2.map = naverMap
        marker2.captionText = "4인용식탁"
        marker3.position = LatLng(37.61845319606045, 127.07425015205975)
        marker3.map = naverMap
        marker3.captionText = "BOLSAK"

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}