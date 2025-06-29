package com.example.wadaihjparty.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wadaihjparty.data.local.entity.OrderHistoryEntity
import com.example.wadaihjparty.databinding.ItemOrderHistoryBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter : ListAdapter<OrderHistoryEntity, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    class HistoryViewHolder(private val binding: ItemOrderHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderHistoryEntity) {
            val details = order.orderDetails.replace(", ", "\n- ")
            binding.tvHistoryDetails.text = details
            binding.tvHistoryCakeNum.text = "Pesanan #${order.id}"
            binding.tvHistoryPrice.text = formatPrice(order.totalPrice)
        }

        private fun formatPrice(price: Double): String {
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            numberFormat.maximumFractionDigits = 0
            return numberFormat.format(price)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OrderHistoryEntity>() {
            override fun areItemsTheSame(oldItem: OrderHistoryEntity, newItem: OrderHistoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: OrderHistoryEntity, newItem: OrderHistoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}