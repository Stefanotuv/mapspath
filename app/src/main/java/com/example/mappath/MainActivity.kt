package com.example.mappath
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import java.util.Locale

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationList = mutableListOf<LatLng>()
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val btnRecord = findViewById<Button>(R.id.btnRecord)
        val btnStop = findViewById<Button>(R.id.btnStop)

        btnRecord.setOnClickListener {
            startRecording()
            btnRecord.isEnabled = false
            btnStop.isEnabled = true
            btnStop.visibility = Button.VISIBLE
        }

        btnStop.setOnClickListener {
            stopRecording()
            btnRecord.isEnabled = true
            btnStop.isEnabled = false
            btnStop.visibility = Button.GONE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            return
        }
        map.isMyLocationEnabled = true
    }

    private fun startRecording() {
        isRecording = true
        locationList.clear()
        requestLocationUpdates()
        Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, TripDetailsActivity::class.java)
//        intent.putParcelableArrayListExtra("locationList", ArrayList(locationList))
//        startActivity(intent)
    }

    private fun stopRecording() {
        isRecording = false
        removeLocationUpdates()
        logRecordingData() // Log the recording data
        Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show()

        // Launch the TripDetailsActivity and pass the recorded locationList
        val intent = Intent(this, TripDetailsActivity::class.java)
        intent.putParcelableArrayListExtra("locationList", ArrayList(locationList))
        startActivity(intent)
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 5000 // 5 seconds interval for location updates
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if (isRecording) {
                    locationResult.locations.forEach { location ->
                        val latLng = LatLng(location.latitude, location.longitude)
                        locationList.add(latLng)
                    }
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun removeLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {})
    }

    private fun drawPathOnMap() {
        val polylineOptions = PolylineOptions().addAll(locationList).color(Color.RED).width(5f)
        map.addPolyline(polylineOptions)

        // Zoom to the path on the map
        val boundsBuilder = LatLngBounds.builder()
        locationList.forEach { boundsBuilder.include(it) }
        val bounds = boundsBuilder.build()
        val padding = 100 // Padding around the path
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }

    private fun logRecordingData() {
        for ((index, latLng) in locationList.withIndex()) {
            val latitude = latLng.latitude
            val longitude = latLng.longitude
            val currentTime = System.currentTimeMillis()

            // Convert the timestamp to a human-readable format (Optional)
            val timeFormatted = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentTime)

            Log.d("LocationLog", "Point $index - Time: $timeFormatted, Latitude: $latitude, Longitude: $longitude")
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}
