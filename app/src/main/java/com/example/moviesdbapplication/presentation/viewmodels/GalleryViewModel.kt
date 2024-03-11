package com.example.moviesdbapplication.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdbapplication.domain.model.ImageResponse
import com.example.moviesdbapplication.domain.usecase.gallery.GetAllImagesFromGalleryUseCase
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class GalleryViewModel @Inject constructor(private val getAllImagesFromGalleryUseCase: GetAllImagesFromGalleryUseCase) :
    ViewModel() {

    private val _imagesLiveData = MutableLiveData<Result<List<ImageResponse>>?>()
    val imagesLiveData: MutableLiveData<Result<List<ImageResponse>>?>
        get() = _imagesLiveData

//    fun saveImage(uri: Uri) = galleryRepository.saveImageToFireStorage(uri)

    fun getAllImages(){
        viewModelScope.launch {
            _imagesLiveData.value = getAllImagesFromGalleryUseCase()
        }
    }
}

