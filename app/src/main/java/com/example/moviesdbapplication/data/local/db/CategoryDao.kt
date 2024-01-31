package com.example.moviesdbapplication.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesdbapplication.data.model.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * from categories")
    fun getAllCategories(): List<CategoryEntity>
}