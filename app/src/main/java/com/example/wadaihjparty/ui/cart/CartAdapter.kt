package com.example.wadaihjparty.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wadaihjparty.databinding.ItemCartBinding
import com.example.wadaihjparty.model.CartItem
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
            Glide.with(ivCartImage.context) // Gunakan ID 'ivCartImage' yang benar
                .load(cartItem.cake.imageUrl.ifEmpty { R.drawable.baseline_cake_24 }) // Muat URL, jika kosong, pakai placeholder
                .placeholder(R.drawable.baseline_cake_24) // Gambar sementara saat loading
                .error(android.R.drawable.ic_dialog_alert) // Gambar error bawaan Android
                .into(ivCartImage) // Gunakan ID 'ivCartImage' yang benar

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
        // Cukup ganti referensi list-nya dengan list yang baru
        items = newItems
        // Beritahu adapter datanya sudah berubah agar UI di-refresh
        notifyDataSetChanged()
    }
}
interface CartItemListener {
    fun onIncreaseClicked(cartItem: CartItem)
    fun onDecreaseClicked(cartItem: CartItem)
}