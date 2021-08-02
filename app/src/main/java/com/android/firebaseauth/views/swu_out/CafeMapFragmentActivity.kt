package com.android.firebaseauth.views.swu_out

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.annotation.NonNull
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.android.firebaseauth.R
import com.android.firebaseauth.views.swu_out.result.cafe.CafeResultActivity
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback


class CafeMapFragmentActivity : Activity(), OnMapReadyCallback {

    // 버튼 변수
    private lateinit var CafebtnList1 : Button
    //private lateinit var CafebtnList2 : Button
    //private lateinit var CafebtnList3 : Button

    // 지도
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
        setContentView(R.layout.out_cafefragment_map)

        mapView = findViewById(R.id.Cafemap_view)
        mapView.onCreate(savedInstanceState)
        // 버튼 연결
        CafebtnList1 = findViewById(R.id.CafebtnList1)

        mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSTION_REQUEST_CODE)

        // 리스트 버튼 누르면 좌표, 장소이름 전달
        CafebtnList1.setOnClickListener {
            var intent = Intent(this, CafeResultActivity::class.java)
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
                13.5 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition

        // 장소 리스트 마커
        marker1.position = LatLng(37.62505733711925, 127.0891451997975)
        marker1.map = naverMap
        marker1.captionText = "토끼별"
        marker2.position = LatLng(37.62237001826222, 127.08681682863418)
        marker2.map = naverMap
        marker2.captionText = "에슬로우"
        marker3.position = LatLng(37.62075303386505, 127.08276298313841)
        marker3.map = naverMap
        marker3.captionText = "에이치큐브"
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