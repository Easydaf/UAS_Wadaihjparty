package com.example.wadaihjparty.model

import com.example.wadaihjparty.domain.model.Cake

data class CartItem(
    val cake: Cake,
    var quantity: Int = 1,
)