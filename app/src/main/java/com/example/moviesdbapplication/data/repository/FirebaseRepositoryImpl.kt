package com.example.moviesdbapplication.data.repository

import android.net.Uri
import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.util.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl(private val storage: FirebaseStorage) : FirebaseRepository {
    override suspend fun getAllImagesFromGallery(): Result<List<ImageResponse>> {
        val listRef = storage.reference.child("images")
        return try {
            val listResult = listRef.listAll().await()
            val imageUrls = mutableListOf<String>()
            val downloadTasks = mutableListOf<Task<Uri>>()

            for (item in listResult.items) {
                val task = item.downloadUrl
                downloadTasks.add(task)
            }

            val uris = Tasks.whenAllSuccess<Uri>(downloadTasks).await()
            for (uri in uris) {
                val imageUrl = uri.toString()
                imageUrls.add(imageUrl)
            }

            val response = imageUrls.map { ImageResponse(it) }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}