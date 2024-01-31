package com.example.moviesdbapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "movie_category_relation", primaryKeys = ["movie_id", "category_id"])
data class MovieCategoryRelationEntity(
    @ColumnInfo(name = "movie_id") val movie_id: Int,
    @ColumnInfo(name = "category_id") val category_id: Int
)