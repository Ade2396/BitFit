package com.example.bitfit.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entries ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Entry>>

    @Insert
    suspend fun insert(entry: Entry)

    @Query("DELETE FROM entries")
    suspend fun clear()
}
