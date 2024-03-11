package com.example.moviesdbapplication.domain.repository

import androidx.lifecycle.LiveData
import com.example.moviesdbapplication.domain.model.LocationData
import com.example.moviesdbapplication.util.Result

interface MapRepository {
    fun getSavedLocations(): LiveData<Result<List<LocationData>>>

    fun saveLocation(locationData: LocationData)
}