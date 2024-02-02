package com.example.moviesdbapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviesdbapplication.R
import com.example.moviesdbapplication.domain.model.Movie

class MovieListAdapter : ListAdapter<Movie, MovieViewHolderV2>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolderV2 {
        return MovieViewHolderV2(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )

    }

    override fun onBindViewHolder(holder: MovieViewHolderV2, position: Int) {
        holder.render(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}