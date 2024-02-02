package com.example.moviesdbapplication.di

object NetworkConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
//    const val API_KEY: String = System.getProperty("MOVIDES_APIKEY")
    const val PATH_IMG = "https://image.tmdb.org/t/p/w500"
    const val FIRESTORAGE = "gs://moviedbexample.appspot.com"
    var pageIndex = 1

    const val DATE_FORMAT_PATTERN = "dd MMMM yyyy 'a las' HH:mm:ss"

}