package com.example.animationandroid.google_map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.animationandroid.databinding.CustomInfoWindowBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker): View {
        val binding = CustomInfoWindowBinding.inflate(LayoutInflater.from(context))
        val title: TextView = binding.infoWindowTitle
        val desc: TextView = binding.infoWindowDesc
        val image: ImageView = binding.infoWindowIv

        val data = marker.tag as? CustomInfoWindowData
        title.text = data?.title
        desc.text = data?.desc
        data?.image?.let { image.setImageResource(it) }

        return binding.root
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }
}