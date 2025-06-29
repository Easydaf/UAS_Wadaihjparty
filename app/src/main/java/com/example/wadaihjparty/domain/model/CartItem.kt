package com.example.wadaihjparty.domain.model

data class CartItem(
    val cake: Cake,
    var quantity: Int = 1,
)