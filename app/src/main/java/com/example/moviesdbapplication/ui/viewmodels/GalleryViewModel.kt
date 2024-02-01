package com.example.moviesdbapplication.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.moviesdbapplication.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val galleryRepository: GalleryRepository) :
    ViewModel() {
    fun saveImage(uri: Uri) = galleryRepository.saveImageToFireStorage(uri)

    fun getAllImages() = galleryRepository.getAllImagesFromGallery()
}