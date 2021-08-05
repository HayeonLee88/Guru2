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
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class FoodMapFragmentActivity : Activity(), OnMapReadyCallback {

    lateinit var dbManager: DBmanager
    lateinit var sqllitedb: SQLiteDatabase

    // 버튼 변수
    private lateinit var FoodbtnList4: Button

    private lateinit var mapView: MapView
    private val LOCATION_PERMISSTION_REQUEST_CODE : Int = 1000
    private lateinit var locationSource : FusedLocationSource // 위치를 반환하는 구현체
    private lateinit var naverMap: NaverMap
    private val marker = Marker()
    private val marker1 = Marker()
    private val marker2 = Marker()
    private val marker3 = Marker()
    // 설명창
    private val infoWindow1 = InfoWindow()
    private val infoWindow2 = InfoWindow()
    private val infoWindow3 = InfoWindow()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.out_foodfragment_map)

        FoodbtnList4 = findViewById(R.id.btnList4)

        mapView = findViewById(R.id.Foodmap_view)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)


        var intent = intent
        var PlaceValue: String? = intent.getStringExtra("place")

        dbManager = DBmanager(this, "PlaceDB", null, 1)
        sqllitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor =
            sqllitedb.rawQuery("SELECT * FROM Place where theme = '서울여대 주변'and category = '음식점';", null)

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


    override fun onMapReady(@NonNull naverMap:NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // 현재 위치 마커
        marker.position = LatLng(37.6281, 127.0905)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED
        // 장소 리스트 마커
        marker1.position = LatLng(37.62423434825952, 127.08959335210993)
        marker1.map = naverMap // 스와레
        marker1.captionText = "스와레"
        marker2.position = LatLng(37.62480566514441, 127.0895438571548)
        marker2.map = naverMap // 고씨네
        marker2.captionText = "고씨네"
        marker3.position = LatLng(37.625048839636904, 127.08902718259473)
        marker3.map = naverMap //타이인플레이트
        marker3.captionText = "타이인플레이트"

        ////////////////////////// 정보창 띄우기 ///////////////////
        infoWindow1.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "일식당"
            }
        }
        infoWindow2.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "카레"
            }
        }
        infoWindow3.adapter = object : InfoWindow.DefaultTextAdapter(application) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return "태국음식"
            }
        }
        infoWindow1.open(marker1) // 정보창 열기
        infoWindow2.open(marker2)
        infoWindow3.open(marker3)

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