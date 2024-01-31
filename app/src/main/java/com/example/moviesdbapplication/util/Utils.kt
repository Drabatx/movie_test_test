package com.example.moviesdbapplication.util

import android.content.res.Resources

object Utils {
    fun getLanguageSystem(): String {
        val locale = Resources.getSystem().configuration.locales[0]
        return locale.toLanguageTag()
    }
}