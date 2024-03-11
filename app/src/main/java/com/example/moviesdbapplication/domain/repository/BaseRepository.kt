package com.example.moviesdbapplication.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviesdbapplication.util.Result
import java.util.concurrent.TimeoutException

open class BaseRepository {

    fun <T> MutableLiveData<Result<T>>.postError(e: Throwable) {
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