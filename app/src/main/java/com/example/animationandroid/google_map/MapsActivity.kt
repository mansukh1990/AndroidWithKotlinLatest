package com.example.animationandroid.google_map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityMapsBinding
import com.example.animationandroid.google_map.BitmapHelper.vectorToBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import java.io.IOException
import androidx.core.graphics.createBitmap

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.clMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
        // mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val latLng = LatLng(26.2389, 73.0243)
        val markerOptions = MarkerOptions()

        mMap.addMarker(
            markerOptions
                .position(latLng)
                .title("Jodhpur")
                .snippet("This is my current location")
                .alpha(3f)
                .draggable(true)
                .flat(true)
                .visible(true)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true


        val latLng2 = LatLng(26.2380, 73.0240)

        val markerOptions2 = MarkerOptions()
        mMap.addMarker(
            markerOptions2
                .position(latLng2)
                .title("Marker2")
                .snippet("This is marker2 location")
                .icon(vectorToBitmap(this, R.drawable.ic_google_maps))
        )

        //circle

        val circleOptions = CircleOptions()
        mMap.addCircle(
            circleOptions
                .center(latLng)
                .radius(200.0)
                .fillColor(R.color.color_light_blue)
                .strokeColor(R.color.color_dark_blue)
                .strokeColor(R.color.color_stroke)
                .strokeWidth(2f)
        )


        //polygon
        mMap.addPolygon(
            PolygonOptions().add(
                LatLng(26.2389, 73.0243),
                LatLng(26.2390, 73.0244),
                LatLng(26.2391, 73.0245),
                LatLng(26.2392, 73.0246),
                LatLng(26.2393, 73.0247),
                LatLng(26.2389, 73.0243)
            )
                .fillColor(R.color.color_polygon)
                .strokeColor(R.color.color_polygon_stroke)

        )


        //groundLay
//        mMap.addGroundOverlay(
//            GroundOverlayOptions()
//                .position(latLng, 1000f, 1000f)
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_contact_profile))
//                .clickable(true)
//        )

        mMap.setOnMapClickListener { latLng ->
            mMap.addMarker(
                markerOptions
                    .position(latLng)
                    .title(latLng.toString())
            )

            val geocoder = Geocoder(this@MapsActivity)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Android 13 (API 33) and above - Asynchronous Listener
                geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1,
                    object : GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            if (!addresses.isEmpty()) {
                                val address = addresses.get(0)
                                // Process address details here on background thread callback
                                Log.d("MapsActivity", "Address: " + address.getAddressLine(0))
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            Log.e("MapsActivity", "Geocoding error: $errorMessage")
                        }
                    })
            } else {
                // Below Android 13 - Legacy Synchronous Call
                try {
                    val addresses =
                        geocoder.getFromLocation(
                            latLng.latitude,
                            latLng.longitude,
                            1
                        ) as ArrayList<Address>?
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        Log.d("MapsActivity", "Address: " + address.getAddressLine(0))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, itemId ->

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

    private fun getBitMapFromDrawable(icGoogleMaps: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val drawable = ResourcesCompat.getDrawable(resources, icGoogleMaps, null)
        if (drawable != null) {
            bitmap = createBitmap(150, 150)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }

        return bitmap
    }
    // No global bicycleIcon field needed anymore
}