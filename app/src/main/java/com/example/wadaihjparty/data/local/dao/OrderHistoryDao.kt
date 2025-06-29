package com.example.wadaihjparty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wadaihjparty.data.local.entity.OrderHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: OrderHistoryEntity)

    @Query("SELECT * FROM order_history_table ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderHistoryEntity>>

    @Query("SELECT COUNT(*) FROM order_history_table")
    fun getOrderCount(): Int

    @Query("DELETE FROM order_history_table WHERE id IN (SELECT id FROM order_history_table ORDER BY timestamp ASC LIMIT 1)")
    fun deleteOldestOrder()

    @Query("SELECT * FROM order_history_table WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllOrdersByUserId(userId: String): Flow<List<OrderHistoryEntity>>
}