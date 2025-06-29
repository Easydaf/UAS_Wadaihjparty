package com.example.wadaihjparty.data.remote.api

import com.example.wadaihjparty.data.remote.dto.CakeDto
import retrofit2.http.GET

interface ApiService {

    @GET("api/cakes")
    suspend fun getCakes(): List<CakeDto>
}