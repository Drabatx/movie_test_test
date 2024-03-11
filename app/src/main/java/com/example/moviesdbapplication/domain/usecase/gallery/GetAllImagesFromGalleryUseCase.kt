package com.example.moviesdbapplication.domain.usecase.gallery

import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.domain.repository.GalleryRepository
import com.example.moviesdbapplication.util.Result
import javax.inject.Inject

class GetAllImagesFromGalleryUseCase @Inject constructor(private val galleryRepository: GalleryRepository) {
    suspend operator fun invoke(): Result<List<ImageResponse>> {
        return try {
            galleryRepository.getAllImagesFromGallery()
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}