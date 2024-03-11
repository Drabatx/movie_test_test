package com.example.moviesdbapplication.domain.repository.impl

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesdbapplication.data.repository.FirebaseRepository
import com.example.moviesdbapplication.domain.repository.GalleryRepository
import com.example.moviesdbapplication.domain.repository.BaseRepository
import com.example.moviesdbapplication.domain.model.GenericResponse
import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.util.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(val firebaseRepository: FirebaseRepository) :
    GalleryRepository, BaseRepository() {

    override suspend fun getAllImagesFromGallery(): Result<List<ImageResponse>> {
        return firebaseRepository.getAllImagesFromGallery()

//        val result = MutableLiveData<Result<List<ImageResponse>>>()
//
//        val storage = Firebase.storage
//        val listRef = storage.reference.child("images")
//
//        result.postValue(Result.Loading)
//        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, e -> result.postError(e) }) {
//            try {
//                listRef.listAll()
//                    .addOnSuccessListener { listResult ->
//                        val imageUrls = mutableListOf<String>()
//                        val downloadTasks = mutableListOf<Task<Uri>>()
//
//                        for (item in listResult.items) {
//                            val task = item.downloadUrl
//                            downloadTasks.add(task)
//                        }
//
//                        Tasks.whenAllSuccess<Uri>(downloadTasks)
//                            .addOnSuccessListener { uris ->
//                                for (uri in uris) {
//                                    val imageUrl = uri.toString()
//                                    imageUrls.add(imageUrl)
//                                }
//
//                                val response = imageUrls.map { ImageResponse(it) }
//                                result.postValue(Result.Success(response))
//                            }
//                            .addOnFailureListener { exception ->
//                                result.postValue(Result.Error(exception))
//                            }
//                    }
//                    .addOnFailureListener {
//                        result.postValue(Result.Error(Exception("Error al obtener imagenes")))
//
//                    }
//            } catch (e: Exception) {
//                result.postValue(Result.Error(Exception("Error al obtener imagenes")))
//            }
//        }
//        return result
    }

    override fun saveImageToFireStorage(photoUri: Uri): LiveData<Result<GenericResponse>> {
        val result = MutableLiveData<Result<GenericResponse>>()
//        result.postValue(Result.Loading)
//        val imageName = "${UUID.randomUUID()}.jpg"
//        val imageRef: StorageReference = storageReference.child("images/$imageName")
//        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, e -> result.postError(e) }) {
//            imageRef.putFile(photoUri)
//                .addOnSuccessListener { taskSnapshot ->
//                    val downloadUrl = taskSnapshot.storage.downloadUrl
//                    try {
//                        result.postValue(
//                            Result.Success(
//                                GenericResponse("Se ha guardado su imagen.")
//                            )
//                        )
//                    } catch (e: Exception) {
//                        Log.e("TAG", "saveImageToFireStorage: ", e)
//                        e.let { result.postError(it) }
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    exception.let { result.postError(it) }
//                }
//
//        }
        return result
    }
}