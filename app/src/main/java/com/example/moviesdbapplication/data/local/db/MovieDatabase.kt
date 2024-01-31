package com.example.moviesdbapplication.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesdbapplication.data.model.CategoryEntity
import com.example.moviesdbapplication.data.model.MovieCategoryRelationEntity
import com.example.moviesdbapplication.data.model.MovieEntity

@Database(entities = [MovieEntity::class, MovieCategoryRelationEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(MyConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun categoryDao(): CategoryDao
    abstract fun movieWithCateogryRelation(): MovieWithCategoryRelationDao

}