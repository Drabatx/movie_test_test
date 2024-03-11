package com.example.moviesdbapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import com.example.moviesdbapplication.domain.repository.MoviesRepository
import com.example.moviesdbapplication.presentation.MovieState
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    fun startCategories() {
        repository.saveCategories()
    }

    fun getMovies(type: MovieState): LiveData<Result<MovieRemoteResponse>> {
        return repository.getMovies(type)
    }
}