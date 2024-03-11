package com.example.moviesdbapplication.domain.repository

import androidx.lifecycle.LiveData
import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import com.example.moviesdbapplication.presentation.MovieState
import com.example.moviesdbapplication.util.Result

interface MoviesRepository {
    fun saveCategories()
    fun getMovies(movieState: MovieState): LiveData<Result<MovieRemoteResponse>>

}