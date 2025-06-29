package com.example.wadaihjparty.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.wadaihjparty.data.local.AppDatabase
import com.example.wadaihjparty.data.local.entity.OrderHistoryEntity
import com.example.wadaihjparty.data.repository.HistoryRepository

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoryRepository

    val allOrderHistory: LiveData<List<OrderHistoryEntity>>

    init {
        val orderHistoryDao = AppDatabase.getDatabase(application).orderHistoryDao()
        repository = HistoryRepository(orderHistoryDao)
        allOrderHistory = repository.getAllOrderHistory().asLiveData()
    }
}