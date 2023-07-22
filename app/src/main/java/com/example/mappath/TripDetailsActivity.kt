package com.example.mappath
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions

class TripDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var locationList: ArrayList<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.tripMapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Retrieve the recorded locationList from the Intent
        locationList = intent.getParcelableArrayListExtra("locationList") ?: arrayListOf()

        // Remove the recorded locationList from the Intent to prevent multiple launches
        intent.removeExtra("locationList")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (locationList.isNotEmpty()) {
            // Add the polyline on the map
            val polylineOptions = PolylineOptions().addAll(locationList).color(Color.RED).width(5f)
            map.addPolyline(polylineOptions)

            // Zoom to the path on the map
            val boundsBuilder = LatLngBounds.builder()
            locationList.forEach { boundsBuilder.include(it) }
            val bounds = boundsBuilder.build()
            val padding = 100 // Padding around the path
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        }
    }
}
