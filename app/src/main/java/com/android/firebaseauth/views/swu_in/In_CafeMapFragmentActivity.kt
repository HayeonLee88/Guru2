package com.android.firebaseauth.views.swu_in

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
import com.android.firebaseauth.views.swu_in.result.food.FoodResultActivity
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class In_CafeMapFragmentActivity : Activity(), OnMapReadyCallback {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    // 버튼 변수
    private lateinit var CafebtnList4: Button

    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()
    private val marker3 = Marker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_cafefragment_map)

        // 세부정보 버튼 연결
        CafebtnList4 = findViewById(R.id.InCafebtnList4)

        mapView = findViewById(R.id.InCafemap_view)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        // 리스트 버튼 누르면 좌표, 장소이름 전달
        CafebtnList4.setOnClickListener {
            var intent = Intent(this, FoodResultActivity::class.java)
            startActivity(intent)
        }

        var intent = intent
        var PlaceValue: String? = intent.getStringExtra("place")

        //getIntent().getBooleanExtra("buttonVisible", false)

        dbManager = DBmanager(this, "PlaceDB", null, 1)
        sqllitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor =
            sqllitedb.rawQuery("SELECT * FROM Place where theme = '서울여대' and category = '카페';", null)

        if (cursor.moveToNext()) {
            CafebtnList4.setVisibility(View.VISIBLE)
            PlaceValue = cursor.getString(cursor.getColumnIndex("place")).toString()
        }
        CafebtnList4.text = PlaceValue

        CafebtnList4.setOnClickListener {
            var intent = Intent(this, UserInputResultActivity::class.java)
            intent.putExtra("place", PlaceValue)
            startActivity(intent)
        }


    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
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
            15.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition

        // 장소 리스트 마커
        marker1.position = LatLng(37.62590786013134, 127.09302582011024)
        marker1.map = naverMap
        marker1.captionText = "뚜레쥬르\n서울여대점"
        marker2.position = LatLng(37.628675313504935, 127.09029240920893)
        marker2.map = naverMap
        marker2.captionText = "카페 가은"
        marker3.position = LatLng(37.628298364798546, 127.09126456977415)
        marker3.map = naverMap
        marker3.captionText = "그라찌에"
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