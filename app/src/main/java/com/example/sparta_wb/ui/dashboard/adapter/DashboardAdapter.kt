package com.example.sparta_wb.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sparta_wb.data.remote.model.CartItem
import com.example.sparta_wb.databinding.ItemProductBinding

class DashboardAdapter(private val items: List<CartItem>) :
    RecyclerView.Adapter<DashboardAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            with(binding) {
                Glide.with(root)
                    .load(item.imageUrl)
                    .into(bookImage)

                productTitle.text = item.title
                bookAuthor.text = item.author
                productPrice.text = "%.2f ₽".format(item.price)
                productStock.text = "В наличии: ${item.stock} шт."
                productDescription.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
