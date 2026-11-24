package com.example.data.repository

import com.example.data.local.ShayariDao
import com.example.data.model.ShayariEntity
import com.example.data.model.ShayariSeedData
import kotlinx.coroutines.flow.Flow

class ShayariRepository(private val shayariDao: ShayariDao) {
    val allShayaris: Flow<List<ShayariEntity>> = shayariDao.observeAllShayaris()
    val favoriteShayaris: Flow<List<ShayariEntity>> = shayariDao.observeFavorites()
    val customShayaris: Flow<List<ShayariEntity>> = shayariDao.observeCustomShayaris()

    fun observeCategory(categoryId: String): Flow<List<ShayariEntity>> =
        shayariDao.observeByCategory(categoryId)

    suspend fun ensureSeeded() {
        if (shayariDao.getShayariCount() == 0) {
            shayariDao.insertAll(ShayariSeedData.curatedShayaris)
        }
    }

    suspend fun toggleFavorite(shayari: ShayariEntity) {
        shayariDao.updateFavorite(shayari.id, !shayari.isFavorite)
    }

    suspend fun saveCustomShayari(
        text: String,
        author: String,
        categoryId: String,
        cardThemeId: String
    ) {
        val cleanText = text.trim()
        if (cleanText.isEmpty()) return
        val cleanAuthor = author.trim().ifEmpty { "यश" }
        shayariDao.insertShayari(
            ShayariEntity(
                text = cleanText,
                author = cleanAuthor,
                category = categoryId,
                cardTheme = cardThemeId,
                searchKeywords = "$categoryId $cleanAuthor custom my shayari",
                isFavorite = false,
                isCustom = true,
                isDailyFeatured = false,
                createdAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteCustomShayari(id: Int) {
        shayariDao.deleteCustomShayari(id)
    }
}
