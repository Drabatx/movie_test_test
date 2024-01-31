package com.example.moviesdbapplication.data.remote.response

import com.example.moviesdbapplication.domain.model.Movie
import com.google.gson.Gson

data class MovieRemoteResponse(
    val page: Int? = null,
    val totalPages: Int? = null,
    val results: List<Movie>? = null,
    val totalResults: Int? = null
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}