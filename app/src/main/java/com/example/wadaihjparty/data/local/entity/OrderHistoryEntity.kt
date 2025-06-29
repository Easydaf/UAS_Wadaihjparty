package com.example.wadaihjparty.data.local.entity // Pastikan package ini sesuai

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_history_table")
data class OrderHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderDetails: String,
    val totalPrice: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String
)