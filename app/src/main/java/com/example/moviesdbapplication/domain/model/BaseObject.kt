package com.example.moviesdbapplication.domain.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder

open class BaseObject {
    override fun toString(): String {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this)
    }

    fun toGson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

}