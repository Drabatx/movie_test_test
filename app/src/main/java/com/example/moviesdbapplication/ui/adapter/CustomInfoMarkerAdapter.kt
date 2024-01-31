package com.example.moviesdbapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.moviesdbapplication.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoMarkerAdapter (private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker): View? {
        // No queremos usar una ventana predeterminada, devolvemos null
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_info_marker, null)

        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)

        val title = marker.title
        val date = marker.snippet

        titleTextView.text = title
        dateTextView.text = date

        return view
    }
}