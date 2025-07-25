package com.example.wadaihjparty.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CakeDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double
)