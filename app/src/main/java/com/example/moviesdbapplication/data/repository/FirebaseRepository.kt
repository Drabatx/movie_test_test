package com.example.moviesdbapplication.data.repository

import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.util.Result

interface FirebaseRepository {
    suspend fun getAllImagesFromGallery(): Result<List<ImageResponse>>
}