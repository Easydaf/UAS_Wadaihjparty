package com.example.wadaihjparty.data.repository

import android.util.Log
import com.example.wadaihjparty.data.local.dao.CakeDao
import com.example.wadaihjparty.data.mapper.toDomain
import com.example.wadaihjparty.data.remote.api.ApiService
import com.example.wadaihjparty.domain.model.Cake
import com.example.wadaihjparty.domain.repository.CakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class CakeRepositoryImpl(private val apiService: ApiService, private val cakeDao: CakeDao) : CakeRepository {

    override fun getCakes(): Flow<List<Cake>> {
        return cakeDao.getAllCakes()
    }

    override suspend fun refreshCakesFromApi() {
        withContext(Dispatchers.IO) {
        try {
            val isCacheEmpty = cakeDao.getAllCakes().first().isEmpty()
            if (isCacheEmpty) {
                Log.d("CacheStrategy", "Cache kosong. Mengambil data baru dari API...")
                val cakeDtoList = apiService.getCakes()
                cakeDao.insertAllCakes(cakeDtoList.toDomain())
                Log.d("CacheStrategy", "Data baru berhasil disimpan ke cache.")
            } else {
                Log.d("CacheStrategy", "Cache sudah ada isinya. Tidak perlu mengambil dari API.")
            }
        } catch (e: Exception) {
            Log.e("CacheStrategy", "Gagal mengambil data dari API. Data dari cache akan digunakan.", e)
        }
    }
}
}