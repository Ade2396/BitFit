package com.example.bitfit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val value: Double,
    val notes: String?,
    val timestamp: Long
)
