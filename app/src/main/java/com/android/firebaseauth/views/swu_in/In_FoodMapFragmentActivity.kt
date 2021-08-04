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
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
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

class In_FoodMapFragmentActivity : Activity(), OnMapReadyCallback {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    // 버튼 변수
    private lateinit var FoodbtnList1: Button
    private lateinit var FoodbtnList4: Button

    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE: Int = 1000;
    private lateinit var locationSource: FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()
    private val marker3 = Marker()

    //설명창
    private val infoWindow1 = InfoWindow()
    private val infoWindow2 = InfoWindow()
    private val infoWindow3 = InfoWindow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_foodfragment_map)

        // 세부정보 버튼 연결
        FoodbtnList1 = findViewById(R.id.InFoodbtnList1)
        FoodbtnList4 = findViewById(R.id.InFoodbtnList4)

        mapView = findViewById(R.id.InFoodmap_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        // 리스트 버튼 누르면 좌표, 장소이름 전달
        FoodbtnList1.setOnClickListener {
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
            sqllitedb.rawQuery("SELECT * FROM Place where category = '음식점';", null)

        if (cursor.moveToNext()) {
            FoodbtnList4.setVisibility(View.VISIBLE)
            PlaceValue = cursor.getString(cursor.getColumnIndex("place")).toString()
        }
        FoodbtnList4.text = PlaceValue

        FoodbtnList4.setOnClickListener {
            var intent = Intent(this, UserInputResultActivity::class.java)
            intent.putExtra("place", PlaceValue)
            startActivity(intent)
        }

    }


    override fun onMapReady(@NonNull naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        //////////////////////////////  마커 띄우기 ////////////////////////////
        // 현재 위치 마커
        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED // 마커 색 빨간색으로 변경

        val cameraPosition = CameraPosition(
            LatLng(37.6281, 127.0905),  // 위치 지정
            15.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition

        // 장소 리스트 마커
        marker1.position = LatLng(37.62590786013134, 127.09302582011024)
        marker1.map = naverMap
        marker1.captionText = "아딸 떡볶이"
        marker2.position = LatLng(37.628675313504935, 127.09029240920893)
        marker2.map = naverMap
        marker2.captionText = "츄밥"
        marker3.position = LatLng(37.62790614488822, 127.09041485870739)
        marker3.map = naverMap
        marker3.captionText = "팬도로시"

        /////////////////////// 정보창 띄우기 ///////////////
        infoWindow1.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "분식"
            }
        }
        infoWindow2.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "주먹밥"
            }
        }
        infoWindow3.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "파스타, 카페"
            }
        }
        infoWindow1.open(marker1)
        infoWindow2.open(marker2)
        infoWindow3.open(marker3)  // 정보창 열기

        // 마커를 클릭하면:
        val listener = Overlay.OnClickListener { overlay ->
            val markerA = overlay as Marker

            if (markerA.infoWindow == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow1.open(marker1)
                infoWindow2.open(marker2)
                infoWindow3.open(marker3)
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow1.close()
                infoWindow2.close()
                infoWindow3.close()
            }

            true
        }

        marker1.onClickListener = listener
        marker2.onClickListener = listener
        marker3.onClickListener = listener

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