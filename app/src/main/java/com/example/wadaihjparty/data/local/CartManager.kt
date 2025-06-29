package com.example.wadaihjparty.data.local

import com.example.wadaihjparty.domain.model.Cake
import com.example.wadaihjparty.model.CartItem


object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    fun addToCart(cake: Cake) {
        val existingItem = cartItems.find { it.cake.name == cake.name }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(cake = cake, quantity = 1))
        }
    }
    fun decreaseQuantity(cartItem: CartItem) {
        val existingItem = cartItems.find { it.cake.name == cartItem.cake.name }
        existingItem?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                cartItems.remove(it)
            }
        }
    }

    fun getTotalItemCount(): Int {
        var totalItems = 0
        for (item in cartItems) {
            totalItems += item.quantity
        }
        return totalItems
    }

    fun calculateTotalPrice(): Double {
        var total: Double = 0.0
        for (item in cartItems) {
            val itemPrice = item.cake.price * item.quantity
            total += itemPrice
        }
        return total
    }

    fun getCartItems(): List<CartItem> {
        return cartItems.toList()
    }

    fun clearCart() {
        cartItems.clear()
    }
}