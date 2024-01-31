package com.example.moviesdbapplication.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.moviesdbapplication.data.model.MovieCategoryRelationEntity
import com.example.moviesdbapplication.data.model.MovieEntity
import com.example.moviesdbapplication.data.model.MovieWithCategories

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies WHERE movie_id IN (SELECT movie_id FROM movie_category_relation WHERE category_id = :category_id)")
    fun getMoviesWithCategories(category_id: Int): List<MovieEntity>


}