package com.example.wadaihjparty.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wadaihjparty.data.local.AppDatabase
import com.example.wadaihjparty.data.local.entity.OrderHistoryEntity
import com.example.wadaihjparty.data.repository.AuthRepository
import com.example.wadaihjparty.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoryRepository
    private val authRepository = AuthRepository()

    init {
        val orderHistoryDao = AppDatabase.getDatabase(application).orderHistoryDao()
        repository = HistoryRepository(orderHistoryDao)
    }

    fun saveOrderToHistory(orderDetails: String, totalPrice: Double) {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            val newOrder = OrderHistoryEntity(
                orderDetails = orderDetails,
                totalPrice = totalPrice,
                userId = userId
            )
            repository.insertOrderAndMaintainLimit(newOrder)
        }
    }
}