package com.example.wadaihjparty.ui.costum // Sesuaikan dengan package-mu

import androidx.lifecycle.ViewModel
import com.example.wadaihjparty.R
import com.example.wadaihjparty.data.local.CartManager
import com.example.wadaihjparty.domain.model.Cake

class CustomCakeViewModel : ViewModel() {


    fun addCustomCakeToCart(
        size: String,
        layer: String,
        theme: String,
        notes: String
    ) {
        val customDescription = "Ukuran: $size, Lapisan: $layer, Tema: $theme, Catatan: $notes"

        var customPrice = 200000.0
        if (size.contains("Sedang")) customPrice += 50000.0
        if (size.contains("Besar")) customPrice += 100000.0
        if (layer.contains("2 Lapis") || layer.contains("2 Lapisan")) customPrice += 75000.0

        val customCake = Cake(
            name = "Kue Custom Pesanan",
            imageUrl = "",
            description = customDescription,
            price = customPrice
        )

        CartManager.addToCart(customCake)
    }
}