package com.danny.note.data.db.color

import androidx.room.*
import com.danny.note.data.model.Color
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(color : Color)

    @Delete
    suspend fun delete(color : Color)

    @Query("SELECT * FROM color ORDER BY id DESC")
    fun getAllColors() : Flow<List<Color>>
}