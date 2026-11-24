package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shayaris")
data class ShayariEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val author: String,
    val category: String,
    val cardTheme: String,
    val searchKeywords: String = "",
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false,
    val isDailyFeatured: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
