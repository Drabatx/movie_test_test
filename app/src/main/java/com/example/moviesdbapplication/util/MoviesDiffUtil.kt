package com.example.moviesdbapplication.util

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesdbapplication.domain.model.Movie

class MoviesDiffUtil(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize()= newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
}