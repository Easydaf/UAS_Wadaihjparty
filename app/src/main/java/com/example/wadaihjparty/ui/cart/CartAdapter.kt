package com.example.wadaihjparty.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wadaihjparty.databinding.ItemCartBinding
import com.example.wadaihjparty.domain.model.CartItem
import com.example.wadaihjparty.R

class CartAdapter(
    private var items: List<CartItem>,
    private val listener: CartItemListener) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = items[position]
        with(holder.binding) {
            tvCartName.text = cartItem.cake.name
            tvQuantity.text = cartItem.quantity.toString()
            Glide.with(ivCartImage.context)
                .load(cartItem.cake.imageUrl.ifEmpty { R.drawable.baseline_cake_24 })
                .placeholder(R.drawable.baseline_cake_24)
                .error(android.R.drawable.ic_dialog_alert)
                .into(ivCartImage)

            btnIncrease.setOnClickListener {
                listener.onIncreaseClicked(cartItem)
            }

            btnDecrease.setOnClickListener {
                listener.onDecreaseClicked(cartItem)
            }
        }
    }
    override fun getItemCount(): Int = items.size
    fun updateData(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
interface CartItemListener {
    fun onIncreaseClicked(cartItem: CartItem)
    fun onDecreaseClicked(cartItem: CartItem)
}