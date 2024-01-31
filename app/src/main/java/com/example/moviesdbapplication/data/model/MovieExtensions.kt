package com.example.moviesdbapplication.data.model

import com.example.moviesdbapplication.domain.model.Movie

class MovieExtensions {
    // Función de extensión para convertir MovieEntity a Movie
    fun MovieEntity.toMovie(): Movie {
        return Movie(
            overview = this.overview,
            originalLanguage = this.originalLanguage,
            originalTitle = this.originalTitle,
            video = this.video,
            title = this.title,
            genreIds = this.genreIds,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            releaseDate = this.releaseDate,
            popularity = this.popularity,
            voteAverage = this.voteAverage,
            id = this.id,
            adult = this.adult,
            voteCount = this.voteCount
        )
    }

    // Función de extensión para convertir Movie a MovieEntity
    fun Movie.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            overview = this.overview,
            originalLanguage = this.originalLanguage,
            originalTitle = this.originalTitle,
            video = this.video,
            title = this.title,
            genreIds = this.genreIds,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            releaseDate = this.releaseDate,
            popularity = this.popularity,
            voteAverage = this.voteAverage,
            adult = this.adult,
            voteCount = this.voteCount
        )
    }
}