package com.example.wadaihjparty.ui.detail // Pastikan package-nya benar

import androidx.lifecycle.ViewModel
import com.example.wadaihjparty.data.local.CartManager
import com.example.wadaihjparty.domain.model.Cake

class DetailViewModel : ViewModel() {

    fun addToCart(cake: Cake) {
        CartManager.addToCart(cake)
    }
}