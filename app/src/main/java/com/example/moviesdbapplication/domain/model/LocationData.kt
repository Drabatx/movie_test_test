package com.example.moviesdbapplication.domain.model

import com.example.moviesdbapplication.di.NetworkConstants.DATE_FORMAT_PATTERN
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val date: String,
    val tag: String
) : BaseObject(){

    fun toHashMap(): HashMap<String, Any> {
        val locationMap = HashMap<String, Any>()
        locationMap["latitude"] = latitude
        locationMap["longitude"] = longitude
        locationMap["date"] = date
        locationMap["tag"] = tag
        return locationMap
    }

    fun formatDateToString(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        return dateFormat.format(date)
    }

    companion object {
        fun parseStringToDate(dateString: String): Date {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
            return dateFormat.parse(dateString) ?: Date()
        }
    }

    fun toLatLon(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }
}