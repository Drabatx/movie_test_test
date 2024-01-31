package com.example.moviesdbapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.moviesdbapplication.domain.model.UserInfo
import com.example.moviesdbapplication.util.Result

interface ProfileRepository {
    fun getUserInfo(): LiveData<Result<UserInfo>>

    fun saveUserInfo(userInfo: UserInfo): LiveData<Result<UserInfo>>
}