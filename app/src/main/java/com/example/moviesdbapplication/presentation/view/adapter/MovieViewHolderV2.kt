package com.example.moviesdbapplication.presentation.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.di.NetworkConstants
import com.example.moviesdbapplication.domain.model.Movie
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.picasso.Picasso

class MovieViewHolderV2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.textMovieName)
    val tvRelease = itemView.findViewById<TextView>(R.id.textReleaseDate)
    val tvRating = itemView.findViewById<TextView>(R.id.textRating)
    val rating = itemView.findViewById<CircularProgressIndicator>(R.id.progressRating)
    val imageMovie = itemView.findViewById<ImageView>(R.id.imageMovie)
    fun render(movie: Movie) {
        tvTitle.text = movie.title
        tvRelease.text = movie.releaseDate
        val voteAverage = movie.voteAverage?.toFloat() ?: 0f
        val formattedVoteAverage = String.format("%.0f%%", voteAverage * 10)
        tvRating.text = formattedVoteAverage
        rating.progress = (voteAverage * 10).toInt()

//        Log.i("TAG", "render: ${movie.posterPath?.getPathImage()}")

        Picasso.get()
            .load(movie.posterPath?.getPathImage())
            .placeholder(R.drawable.drawable_placeholder)
            .error(R.drawable.drawable_error)
            .into(imageMovie)

    }

    fun String.getPathImage(): String {
        return NetworkConstants.PATH_IMG.plus(this)
    }
}