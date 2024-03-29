package com.example.moviesdbapplication.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.moviesdbapplication.domain.repository.ProfileRepository
import com.example.moviesdbapplication.domain.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) :
    ViewModel() {

    fun saveUser(userInfo: UserInfo) = repository.saveUserInfo(userInfo)

    fun getUserInfo() = repository.getUserInfo()
}