package com.example.moviesdbapplication.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesdbapplication.domain.repository.MapRepository
import com.example.moviesdbapplication.domain.model.LocationData
import com.example.moviesdbapplication.util.Result
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val locationsCollection: CollectionReference) :
    MapRepository {


    override fun getSavedLocations(): LiveData<Result<List<LocationData>>> {
        val result = MutableLiveData<Result<List<LocationData>>>()
        result.postValue(Result.Loading)
        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, e -> result.postError(e) }) {
            locationsCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    val locationsList = mutableListOf<LocationData>()
                    for (document in querySnapshot.documents) {
                        val dynamicValue = document.id
                        val locationReference = locationsCollection.document(dynamicValue)

                        locationReference.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    val data = documentSnapshot.data
                                    if (data != null) {
                                        val latitude = data["latitude"] as? Double ?: 0.0
                                        val longitude = data["longitude"] as? Double ?: 0.0
                                        val date = data["date"] as? String ?: ""
                                        val tag = data["tag"] as? String ?: ""
                                        val locationData =
                                            LocationData(latitude, longitude, date, tag)
                                        locationsList.add(locationData)
                                    }
                                }
                                result.postValue(Result.Success(locationsList))
                            }.addOnFailureListener { innerException ->
                                result.postError(innerException)
                            }
                    }
                }.addOnFailureListener { e ->
                    result.postError(e)
                }
        }
        return result
    }

    /*Agregar autenticacion para que solo sea por sesion el guardar estos datos*/
    override fun saveLocation(locationData: LocationData) {
        locationsCollection.document(System.currentTimeMillis().toString())
            .set(locationData.toHashMap())
    }

    private fun <T> MutableLiveData<Result<T>>.postError(e: Throwable) {
        value = when (e) {
            is TimeoutException -> {
                Result.Error(e)
            }

            else -> {
                Result.Error(e)
            }
        }
    }
}