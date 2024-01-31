package com.example.moviesdbapplication.data.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationService {
    suspend fun getCurrentLocation(context: Context): Location? {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isPermissionGranted = true
        val isGPSProvider = locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ) || locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

        if (!isGPSProvider || !isPermissionGranted) {
            return null
        }
        return suspendCancellableCoroutine { cont ->
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                cont.resume(null) {}
                return@suspendCancellableCoroutine
            }
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result) {}
                    } else {
                        cont.resume(null) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { cont.resume(it) {} }
                addOnFailureListener { cont.resume(null) {} }
                addOnCanceledListener { cont.resume(null) {} }
            }
        }
    }
}