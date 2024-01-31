package com.example.moviesdbapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import com.example.moviesdbapplication.data.repository.MoviesRepository
import com.example.moviesdbapplication.domain.model.Movie
import com.example.moviesdbapplication.ui.MovieState
import com.example.moviesdbapplication.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeoutException
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