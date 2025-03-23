package com.example.sparta_wb.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.databinding.ItemProductBinding

class CardAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<CardAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productTitle.text = product.title
            binding.productDescription.text = product.description
            binding.productPrice.text = "Цена: ${product.price}$"
            binding.productStock.text = "В наличии: ${product.stock} шт."
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()


    }
}
