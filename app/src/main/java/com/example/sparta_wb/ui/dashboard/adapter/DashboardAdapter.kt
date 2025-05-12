// com/example/sparta_wb/ui/dashboard/adapter/DashboardAdapter.kt
package com.example.sparta_wb.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sparta_wb.data.remote.model.Order
import com.example.sparta_wb.databinding.ItemOrderBinding
import java.text.SimpleDateFormat
import java.util.*

class DashboardAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<DashboardAdapter.OrderViewHolder>() {

    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            with(binding) {
                // Display order info
                orderId.text = "Order #${order.id}"
                orderDate.text = "Ordered on ${dateFormat.format(order.orderAt)}"
                orderStatus.text = "Status: ${order.status.capitalize()}"

                // Display first product as the main image
                order.products.firstOrNull()?.product?.images?.firstOrNull()?.let { imageUrl ->
                    Glide.with(root)
                        .load(imageUrl)
                        .into(orderImage)
                }

                // Display product count
                val itemCount = order.products.sumOf { it.product_quantity }
                productCount.text = "$itemCount items"

                // Display total price
                val total = order.products.sumOf {
                    it.product_unit_price.toDouble() * it.product_quantity
                }
                totalPrice.text = "$${"%.2f".format(total)}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size
}