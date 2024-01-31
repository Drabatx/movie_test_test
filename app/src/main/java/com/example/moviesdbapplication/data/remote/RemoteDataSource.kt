package com.example.moviesdbapplication.data.remote

import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) {
    suspend fun getMovies(url: String, language: String, pageIndex: Int): MovieRemoteResponse {
        return movieApiService.getMovies(url, language, pageIndex)
    }
}