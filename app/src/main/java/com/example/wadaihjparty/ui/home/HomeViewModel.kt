package com.example.wadaihjparty.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wadaihjparty.data.local.AppDatabase
import com.example.wadaihjparty.data.remote.api.RetrofitClient
import com.example.wadaihjparty.data.repository.CakeRepositoryImpl
import com.example.wadaihjparty.domain.model.Cake
import com.example.wadaihjparty.domain.repository.CakeRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val cakeRepository: CakeRepository

    val cakeList: LiveData<List<Cake>>

    init {
        val cakeDao = AppDatabase.getDatabase(application).cakeDao()
        val apiService = RetrofitClient.apiService
        cakeRepository = CakeRepositoryImpl(apiService, cakeDao)

        cakeList = cakeRepository.getCakes().asLiveData()

        refreshCakes()
    }

    fun refreshCakes() {
        viewModelScope.launch {
            cakeRepository.refreshCakesFromApi()
        }
    }
}