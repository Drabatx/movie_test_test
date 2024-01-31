package com.example.moviesdbapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdbapplication.domain.model.Movie
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey @ColumnInfo("movie_id") val id: Int,
    val overview: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val video: Boolean?,
    val title: String?,
    val genreIds: List<Int?>?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val popularity: Double?,
    val voteAverage: Double?,
    val adult: Boolean?,
    val voteCount: Int?
){
    override fun toString(): String {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this)
    }

    fun toMovie(): Movie {
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
}