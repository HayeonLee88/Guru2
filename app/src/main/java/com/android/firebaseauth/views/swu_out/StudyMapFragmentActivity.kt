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
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.android.firebaseauth.R
import com.android.firebaseauth.views.DBmanager
import com.android.firebaseauth.views.swu_in.UserInputResult.UserInputResultActivity
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class StudyMapFragmentActivity : Activity(), OnMapReadyCallback {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    // 버튼 변수
    private lateinit var StudybtnList4: Button

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
        setContentView(R.layout.out_studyfragment_map)

        StudybtnList4 = findViewById(R.id.StudybtnList4)

        mapView = findViewById(R.id.Studymap_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        var intent = intent
        var PlaceValue: String? = intent.getStringExtra("place")

        dbManager = DBmanager(this, "PlaceDB", null, 1)
        sqllitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor =
            sqllitedb.rawQuery("SELECT * FROM Place where theme = '서울여대 주변' and category = '스터디 공간';", null)

        if (cursor.moveToNext()) {
            StudybtnList4.setVisibility(View.VISIBLE)
            PlaceValue = cursor.getString(cursor.getColumnIndex("place")).toString()
        }
        StudybtnList4.text = PlaceValue

        StudybtnList4.setOnClickListener {
            var intent = Intent(this, UserInputResultActivity::class.java)
            intent.putExtra("place", PlaceValue)
            startActivity(intent)
        }

    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val cameraPosition = CameraPosition(
            LatLng(37.6281, 127.0905),  // 위치 지정
            13.3 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition

        // 현재 위치 마커
        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED //현재 위치 마커 색 빨간색으로 변경

        // 장소 리스트 마커
        marker1.position = LatLng(37.625082829560505, 127.08899499608486)
        marker1.map = naverMap
        marker1.captionText = "열공다방 스터디카페"
        marker2.position = LatLng(37.619587503810315, 127.08408257755404)
        marker2.map = naverMap
        marker2.captionText = "멘토즈스터디카페\n화랑대역점"
        marker3.position = LatLng(37.62251142663857, 127.08003012840702)
        marker3.map = naverMap
        marker3.captionText = "작심스터디카페\n서울화랑대역점"

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