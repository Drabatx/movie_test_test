package com.example.moviesdbapplication.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.moviesdbapplication.data.model.MovieCategoryRelationEntity
import com.example.moviesdbapplication.data.model.MovieEntity

@Dao
interface MovieWithCategoryRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCategoryRelation(relation: MovieCategoryRelationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFromList(relations: List<MovieCategoryRelationEntity>)

}