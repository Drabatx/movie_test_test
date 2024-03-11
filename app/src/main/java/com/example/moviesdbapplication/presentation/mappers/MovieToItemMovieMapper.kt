package com.example.moviesdbapplication.presentation.mappers

import com.example.moviesdbapplication.domain.model.Movie
import com.example.moviesdbapplication.presentation.model.MovieItem

class MovieToItemMovieMapper {
    fun map(movie: Movie): MovieItem{
        return MovieItem(movie.title ?: "", movie.releaseDate?:"", movie.voteAverage?:5.0, movie.posterPath?:"")
    }
}