package com.example.animationandroid.google_map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.animationandroid.R
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoWindowAdapter(
    private val context: Context
): GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        // 1. Inflate view and set title, snippet/address, or any custom details
        val view = LayoutInflater.from(context).inflate(
            R.layout.marker_info_contents, null
        )
        
        view.findViewById<TextView>(R.id.text_view_title).text = marker.title
        
        val addressTextView = view.findViewById<TextView>(R.id.text_view_address)
        if (!marker.snippet.isNullOrEmpty()) {
            addressTextView.text = marker.snippet
            addressTextView.visibility = View.VISIBLE
        } else {
            addressTextView.visibility = View.GONE
        }

        // Hide rating by default if not available, or use marker tag if provided
        view.findViewById<TextView>(R.id.text_view_rating).visibility = View.GONE

        return view

    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}