package com.example.animationandroid.google_map

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityGoogleMapWithCustomInfoWindowBinding
import com.example.animationandroid.google_map.BitmapHelper.vectorToBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapWithCustomInfoWindowActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapWithCustomInfoWindowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGoogleMapWithCustomInfoWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.clMap) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.radioGroup.setOnCheckedChangeListener { _, itemId ->

            when (itemId) {
                R.id.radio_normal -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                }

                R.id.radio_satellite -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                }

                R.id.radio_hybrid -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                }

                R.id.radio_terrain -> {
                    mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        val markerOption = MarkerOptions()
        markerOption
            .position(sydney)
            .title("Marker in Sydney")
            .snippet("This is my current location")
            .icon(vectorToBitmap(this, R.drawable.ic_google_maps))
        val marker = mMap.addMarker(markerOption)
        marker?.tag = CustomInfoWindowData(
            "XYZ Hotel",
            getString(R.string.title_activity_google_map_cluster),
            R.drawable.ic_contact_profile
        )
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(this))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }
}