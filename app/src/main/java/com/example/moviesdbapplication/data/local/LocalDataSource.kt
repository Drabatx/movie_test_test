package com.example.moviesdbapplication.data.local

import com.example.moviesdbapplication.data.local.db.CategoryDao
import com.example.moviesdbapplication.data.local.db.MovieDao
import com.example.moviesdbapplication.data.local.db.MovieWithCategoryRelationDao
import com.example.moviesdbapplication.data.model.CategoryEntity
import com.example.moviesdbapplication.data.model.MovieCategoryRelationEntity
import com.example.moviesdbapplication.data.model.MovieEntity
import com.example.moviesdbapplication.domain.model.Movie
import com.example.moviesdbapplication.ui.MovieState
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val movieWithCategoryRelationDao: MovieWithCategoryRelationDao,
    private val categoryDao: CategoryDao
) {
    suspend fun insertCategories() {
        val categories = listOf(
            CategoryEntity(id = MovieState.POPULAR.id, MovieState.POPULAR.name),
            CategoryEntity(id = MovieState.TOP.id, MovieState.TOP.name),
            CategoryEntity(id = MovieState.RECOMMENDED.id, MovieState.RECOMMENDED.name),
        )
        categoryDao.insertCategories(categories)
    }

    suspend fun insertMovies(movies: List<Movie>, movieState: MovieState) {
        val categories =  categoryDao.getAllCategories()
        if (categories.isNotEmpty()){
            insertCategories()
        }
        val moviesList = movies.map { movie ->
            movie.toMovieEntity()
        }
        movieDao.insertMovies(moviesList)

        val movieCategoryRelationEntity = movies.map { movie ->
            MovieCategoryRelationEntity(movie_id = movie.id, category_id = movieState.id)
        }
        movieWithCategoryRelationDao.insertFromList(movieCategoryRelationEntity)
    }

    suspend fun getMoviesByCategory(movieState: MovieState): List<MovieEntity> {
        return movieDao.getMoviesWithCategories(movieState.id)
    }

}