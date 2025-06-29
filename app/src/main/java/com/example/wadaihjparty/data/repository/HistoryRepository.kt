package com.example.wadaihjparty.data.repository

import com.example.wadaihjparty.data.local.dao.OrderHistoryDao
import com.example.wadaihjparty.data.local.entity.OrderHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class HistoryRepository(private val orderHistoryDao: OrderHistoryDao) {

    fun getAllOrderHistory() : Flow<List<OrderHistoryEntity>> {
        val userId = Firebase.auth.currentUser?.uid
        return if (userId != null) {
            orderHistoryDao.getAllOrdersByUserId(userId)
        } else {
            flowOf(emptyList())
        }
    }

    suspend fun insertOrderAndMaintainLimit(order: OrderHistoryEntity) {
        withContext(Dispatchers.IO) {
            val count = orderHistoryDao.getOrderCount()
            if (count >= 10) {
                orderHistoryDao.deleteOldestOrder()
            }

            orderHistoryDao.insertOrder(order)
        }
    }
}