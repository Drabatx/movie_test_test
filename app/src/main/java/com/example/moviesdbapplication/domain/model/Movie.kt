package com.example.moviesdbapplication.domain.model

import com.example.moviesdbapplication.data.model.MovieEntity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

data class Movie(

	@field:SerializedName("overview")
	val overview: String?,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
):BaseObject(){

	fun toMovieEntity(): MovieEntity {
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
