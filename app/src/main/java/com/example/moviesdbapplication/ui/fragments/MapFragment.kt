package com.example.moviesdbapplication.ui.fragments

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.data.service.LocationService
import com.example.moviesdbapplication.databinding.FragmentMapBinding
import com.example.moviesdbapplication.domain.model.LocationData
import com.example.moviesdbapplication.ui.adapter.CustomInfoMarkerAdapter
import com.example.moviesdbapplication.ui.viewmodels.MapViewModel
import com.example.moviesdbapplication.util.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : MyBaseFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var googleMap: GoogleMap

    private val viewModel by viewModels<MapViewModel>()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS
    )

    private val locationService: LocationService = LocationService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

    }

    private fun obtainLatLon() {
        lifecycleScope.launch {
            while (true) {
                val result = locationService.getCurrentLocation(requireContext())
                if (result != null) {
                    viewModel.saveLocation(result.toLocationData())
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(result.toLatLon(), 16f))
                    showNotification("Se ha guardado su ubicacion")
                }
                delay(1 * 60 * 1000) // Espera 5 minutos antes de obtener la siguiente ubicación
            }
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setInfoWindowAdapter(CustomInfoMarkerAdapter(requireContext()))

        viewModel.getLocations().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    result.data.forEach { location ->
                        val marker = googleMap.addMarker(
                            MarkerOptions().position(location.toLatLon()).title(location.tag)
                                .snippet(location.date)
                        )
                        marker.showInfoWindow()
                    }
                    hideLoading()
                }

                is Result.Error -> {
                    hideLoading()
                    result.exception.message?.let { showError(it) }
                }

                else -> {
                    showLoading()
                }
            }
        }
        getCurrentLocation()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    private fun havePermissions(): Boolean {
        return locationPermissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getCurrentLocation() {
        if (havePermissions()) {
            obtainLatLon()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                locationPermissions[0]
            )
        ) {
            obtainLatLon()
        } else {
            requestLocationPermissionLauncher.launch(locationPermissions)
        }
    }

    private var requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                obtainLatLon()
            } else {
                requestLocationPermissions()
            }
        }

    fun Location.toLatLon(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }

    fun Location.toLocationData(): LocationData {
        return LocationData(this.latitude, this.longitude, viewModel.getCurrentDate(), "Posicion")
    }

    fun showNotification(message: String) {
        val channelId = "mi_canal_de_notificaciones"
        val notificationId = 1448
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.baseline_notification) // Reemplaza con tu propio ícono
            .setContentTitle("Atencion")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel(requireContext(), channelId)

        // Mostrar la notificación
        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "My App",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal de notificaciones"
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}