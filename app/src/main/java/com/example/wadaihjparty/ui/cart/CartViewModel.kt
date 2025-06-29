package com.example.wadaihjparty.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wadaihjparty.data.local.CartManager
import com.example.wadaihjparty.model.CartItem
class CartViewModel : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()


    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    fun reloadData() {
        loadCartItems()
    }

    init {

        loadCartItems()
    }

    private fun loadCartItems() {
        _cartItems.value = CartManager.getCartItems()
        calculateTotalPrice()
    }

    private fun calculateTotalPrice() {
        _totalPrice.value = CartManager.calculateTotalPrice()
    }

    fun increaseQuantity(cartItem: CartItem) {
        CartManager.addToCart(cartItem.cake)
        loadCartItems()
    }

    fun decreaseQuantity(cartItem: CartItem) {
        CartManager.decreaseQuantity(cartItem)
        loadCartItems()
    }

}