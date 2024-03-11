package com.example.moviesdbapplication.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesdbapplication.domain.repository.ProfileRepository
import com.example.moviesdbapplication.domain.model.UserInfo
import com.example.moviesdbapplication.util.Result
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val usersCollection: DocumentReference) :
    ProfileRepository {

    override fun getUserInfo(): LiveData<Result<UserInfo>> {
        val result = MutableLiveData<Result<UserInfo>>()
        result.postValue(Result.Loading)
        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, e -> result.postError(e) }) {
            usersCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.exists()) {
                        val data = querySnapshot.data
                        if (data != null) {
                            val name = data["nombre"] as? String ?: ""
                            val email = data["email"] as? String ?: ""
                            val phone = data["telefono"] as? String ?: ""
                            result.postValue(Result.Success(UserInfo(name, email, phone)))
                        } else {
                            result.postError(Exception("No se encontro Informacion"))
                        }
                    } else {
                        result.postError(Exception("No se encontro Informacion"))
                    }
                }.addOnFailureListener { e ->
                    result.postError(Exception("No se encontro Informacion"))
                }
        }
        return result
    }

    override fun saveUserInfo(userInfo: UserInfo) : LiveData<Result<UserInfo>>{
        val result = MutableLiveData<Result<UserInfo>>()
        result.postValue(Result.Loading)
//        CoroutineScope(Dispatchers.IO).launch(CoroutineExceptionHandler { _, e -> result.postError(e) }) {
            usersCollection.set(userInfo.toHashMap())
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        result.postValue(Result.Success(userInfo))
                    }else{
                        task.exception?.let { result.postError(it) } ?: result.postError(NullPointerException())
                    }
                }
//        }

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