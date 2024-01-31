package com.example.moviesdbapplication.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithCategories(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "category_id",
        associateBy = Junction(MovieCategoryRelationEntity::class)
    )
    val categories: List<CategoryEntity>
)