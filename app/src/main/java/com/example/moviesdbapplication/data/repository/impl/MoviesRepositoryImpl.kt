package com.example.moviesdbapplication.data.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesdbapplication.data.local.LocalDataSource
import com.example.moviesdbapplication.data.model.CategoryEntity
import com.example.moviesdbapplication.data.remote.RemoteDataSource
import com.example.moviesdbapplication.data.remote.response.MovieRemoteResponse
import com.example.moviesdbapplication.data.repository.MoviesRepository
import com.example.moviesdbapplication.di.NetworkConstants
import com.example.moviesdbapplication.ui.MovieState
import com.example.moviesdbapplication.util.Result
import com.example.moviesdbapplication.util.Utils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MoviesRepository {


    override fun saveCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.insertCategories()
        }
    }

    override fun getMovies(type: MovieState): LiveData<Result<MovieRemoteResponse>> {
        val result = MutableLiveData<Result<MovieRemoteResponse>>()

        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, e -> result.postError(e) }) {
            try {
                val response =
                    remoteDataSource.getMovies(type.url, Utils.getLanguageSystem(), NetworkConstants.pageIndex)

                response.results?.let { movies ->
                    localDataSource.insertMovies(movies, type)
                }

                result.postValue(Result.Success(response))
            } catch (e: Exception) {
                try {
                    val cachedMovies = when(type){
                        MovieState.POPULAR-> localDataSource.getMoviesByCategory(type).sortedByDescending { it.popularity }
                        MovieState.TOP -> localDataSource.getMoviesByCategory(type).sortedByDescending { it.voteAverage }
                        MovieState.RECOMMENDED -> localDataSource.getMoviesByCategory(type)
                    }
                    if (cachedMovies.isNotEmpty()) {
                        val cachedMovieResponse =
                            MovieRemoteResponse(results = cachedMovies.map { it.toMovie() })
                        result.postValue(Result.Success(cachedMovieResponse))
                    } else {
                        result.postValue(Result.Error(e))
                    }
                } catch (databaseException: Exception) {
                    result.postValue(Result.Error(e))
                }
            }
        }

        return result
    }

    private fun <T> MutableLiveData<Result<T>>.postError(e: Throwable) {
        value = when (e) {
            is TimeoutException -> {
                Result.Error(e)
            }

            else -> {
                Result.Error(e)
            }
        }
    }
}