package com.example.moviesdbapplication.ui

enum class MovieState(val id: Int, val url: String) {
    POPULAR(1, "movie/popular"), TOP(2, "movie/top_rated"), RECOMMENDED(3, "trending/movie/day")
}