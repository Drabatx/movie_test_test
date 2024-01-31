package com.example.moviesdbapplication.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyConverters {

    @TypeConverter
    fun fromString(value: String?): List<Int?>? {
        return value?.let {
            Gson().fromJson(value, object : TypeToken<List<Int?>?>() {}.type)
        }
    }

    @TypeConverter
    fun fromList(list: List<Int?>?): String? {
        return Gson().toJson(list)
    }
}