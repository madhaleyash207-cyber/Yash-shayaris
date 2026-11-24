package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ShayariEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShayariDao {
    @Query("SELECT * FROM shayaris ORDER BY isCustom DESC, id ASC")
    fun observeAllShayaris(): Flow<List<ShayariEntity>>

    @Query("SELECT * FROM shayaris WHERE category = :categoryId ORDER BY isCustom DESC, id ASC")
    fun observeByCategory(categoryId: String): Flow<List<ShayariEntity>>

    @Query("SELECT * FROM shayaris WHERE isFavorite = 1 ORDER BY createdAt DESC, id DESC")
    fun observeFavorites(): Flow<List<ShayariEntity>>

    @Query("SELECT * FROM shayaris WHERE isCustom = 1 ORDER BY createdAt DESC, id DESC")
    fun observeCustomShayaris(): Flow<List<ShayariEntity>>

    @Query("UPDATE shayaris SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShayari(shayari: ShayariEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shayaris: List<ShayariEntity>)

    @Query("DELETE FROM shayaris WHERE id = :id AND isCustom = 1")
    suspend fun deleteCustomShayari(id: Int)

    @Query("SELECT COUNT(*) FROM shayaris")
    suspend fun getShayariCount(): Int
}
