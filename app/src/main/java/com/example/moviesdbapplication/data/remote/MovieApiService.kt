package com.example.moviesdbapplication.data.remote

import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("{url}")
    suspend fun getMovies(@Path("url", encoded = true) url: String, @Query("language") language: String, @Query("page") page: Int):MovieRemoteResponse

}