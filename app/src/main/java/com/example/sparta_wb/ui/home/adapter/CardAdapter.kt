package com.example.sparta_wb.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sparta_wb.R
import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.databinding.ItemProductBinding

class CardAdapter(
    private var productList: List<Product>,
    private val fragment: Fragment
) : RecyclerView.Adapter<CardAdapter.ProductViewHolder>() {

    private var originalList: List<Product> = productList

    fun updateOriginalList(newList: List<Product>) {
        originalList = newList
    }

    fun filter(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            originalList
        } else {
            originalList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
        updateList(filteredList)
    }

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            val imageUrl = product.images.firstOrNull()
            if (imageUrl != null) {
                Glide.with(root.context)
                    .load(imageUrl)
                    .placeholder(R.color.black)
                    .error(R.color.black)
                    .into(bookImage)
            } else {
                bookImage.setImageResource(R.color.black)
            }

            productTitle.text = product.title
            productPrice.text = "Цена: ${product.price}$"
            productStock.text = "В наличии: ${product.stock} шт."

            root.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("product", product)
                    putString("image_path", imageUrl)
                }
                NavHostFragment.findNavController(fragment)
                    .navigate(R.id.action_navigation_home_to_detailsFragment, bundle)
            }
        }
    }
}
