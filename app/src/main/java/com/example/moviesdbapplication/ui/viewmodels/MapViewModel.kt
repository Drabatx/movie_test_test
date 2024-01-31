package com.example.moviesdbapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdbapplication.data.repository.MapRepository
import com.example.moviesdbapplication.di.NetworkConstants
import com.example.moviesdbapplication.domain.model.LocationData
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: MapRepository): ViewModel() {
    fun getLocations(): LiveData<Result<List<LocationData>>> {
        return repository.getSavedLocations()
    }

    fun saveLocation(location: LocationData) {
        repository.saveLocation(location)
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat(NetworkConstants.DATE_FORMAT_PATTERN, Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}