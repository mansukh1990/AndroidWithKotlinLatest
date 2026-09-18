package com.example.animationandroid.google_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.animationandroid.databinding.ActivityGoogleMapClusterBinding
import com.google.maps.android.clustering.ClusterManager

class GoogleMapClusterActivity : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapClusterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGoogleMapClusterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{googleMap ->
            addClusteredMarkers(googleMap)

        }
    }

    private val places: List<Place> = listOf(
        Place("Mehrangarh Fort", LatLng(26.2495, 73.0243), "Fort Road, Jodhpur", 4.8f),
        Place("Jaswant Thada", LatLng(26.2525, 73.0250), "Near Mehrangarh Fort, Jodhpur", 4.6f),
        Place("Umaid Bhawan Palace", LatLng(26.2311, 73.0514), "Circuit House Rd, Jodhpur", 4.7f),
        Place("Ghanta Ghar Clock Tower", LatLng(26.2441, 73.0256), "Nai Sarak, Jodhpur", 4.3f),
        Place("Mandore Gardens", LatLng(26.2913, 73.0450), "Mandore, Jodhpur", 4.5f)
    )

    private fun addClusteredMarkers(googleMap: GoogleMap) {
        mMap = googleMap
        // Create the ClusterManager class and set the custom renderer.
        val clusterManager = ClusterManager<Place>(this, googleMap)
        clusterManager.renderer =
            PlaceRenderer(
                this,
                googleMap,
                clusterManager
            )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        // Add the places to the ClusterManager.
        clusterManager.addItems(places)
        clusterManager.cluster()

        // Set ClusterManager as the OnCameraIdleListener so that it
        // can re-cluster when zooming in and out.
        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }

        // Move camera to the initial location of the places
        val jodhpur = LatLng(26.2495, 73.0243)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jodhpur, 12f))
    }

}