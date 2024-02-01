package com.example.moviesdbapplication.di

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1Y2RjNDIyYzJjMGE3YTg2ZWYwYzFmMDBmMDA3YjAxZSIsInN1YiI6IjYxYWYwZTBiYjViYzIxMDA0MzYyODdiYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HTsl1DvFmvGuj8TNj3HOvSWIhTBWuCKSYdtYS_6FclM"
    const val PATH_IMG = "https://image.tmdb.org/t/p/w500"
    const val FIRESTORAGE = "gs://moviedbexample.appspot.com"
    var pageIndex = 1

    const val DATE_FORMAT_PATTERN = "dd MMMM yyyy 'a las' HH:mm:ss"

}