package com.example.moviesdbapplication.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.moviesdbapplication.domain.model.GenericResponse
import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.util.Result

interface GalleryRepository {
    fun saveImageToFireStorage(photoUri: Uri): LiveData<Result<GenericResponse>>

    fun getAllImagesFromGallery(): LiveData<Result<List<ImageResponse>>>
}