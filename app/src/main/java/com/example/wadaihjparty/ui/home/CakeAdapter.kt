package com.example.wadaihjparty.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wadaihjparty.databinding.ItemCakeBinding
import com.example.wadaihjparty.domain.model.Cake
import com.example.wadaihjparty.R

class CakeAdapter(
    private var cakeList: List<Cake>,
    private val onItemClick: (Cake) -> Unit,
) : RecyclerView.Adapter<CakeAdapter.CakeViewHolder>() {

    inner class CakeViewHolder(val binding: ItemCakeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val binding = ItemCakeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CakeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        val cake = cakeList[position]
        with(holder.binding) {
            tvCakeDetailName.text = cake.name
            tvHistoryPrice.text = cake.formattedPrice
            Glide.with(ivCakeImage.context)
                .load(cake.imageUrl)
                .placeholder(R.drawable.baseline_cake_24)
                .error(R.drawable.baseline_cake_24)
                .into(ivCakeImage)

            root.setOnClickListener {
                onItemClick(cake)
            }
        }
    }


    override fun getItemCount(): Int = cakeList.size
    fun updateData(newCakeList: List<Cake>) {
        this.cakeList = newCakeList
        // Memberitahu adapter untuk me-refresh seluruh tampilannya dengan data baru
        notifyDataSetChanged()
    }
}