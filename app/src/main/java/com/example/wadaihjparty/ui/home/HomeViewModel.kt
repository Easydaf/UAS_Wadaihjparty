package com.example.wadaihjparty.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        val cakeDao = AppDatabase.getDatabase(application).cakeDao()
        val apiService = RetrofitClient.apiService
        cakeRepository = CakeRepositoryImpl(apiService, cakeDao)

        cakeList = cakeRepository.getCakes().asLiveData()

        refreshCakes()
    }

    fun refreshCakes() {
        _isLoading.value = true
        viewModelScope.launch {
            cakeRepository.refreshCakesFromApi()
            _isLoading.value = false
        }
    }
}