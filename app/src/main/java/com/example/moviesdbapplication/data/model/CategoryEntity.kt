package com.example.moviesdbapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo("category_id") val id: Int,
    @ColumnInfo("name")val name: String
)
