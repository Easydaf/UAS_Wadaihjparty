package com.example.wadaihjparty.domain.repository

import com.example.wadaihjparty.domain.model.Cake
import kotlinx.coroutines.flow.Flow

interface CakeRepository {
    fun getCakes(): Flow<List<Cake>>
    suspend fun refreshCakesFromApi()
}