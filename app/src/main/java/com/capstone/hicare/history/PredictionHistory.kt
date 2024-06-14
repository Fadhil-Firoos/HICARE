package com.capstone.hicare.history

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "prediction_history")
data class PredictionHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imagePath: String,
    val result: String,
)

