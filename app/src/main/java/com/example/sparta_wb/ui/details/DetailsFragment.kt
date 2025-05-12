package com.example.sparta_wb.ui.details

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sparta_wb.R
import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.databinding.FragmentDetailsBinding
import java.io.File

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val product = arguments?.getSerializable("product") as? Product

        product?.let {
            binding.productTitle.text = it.title
            binding.productPrice.text = "Цена: ${it.price}$"
            binding.productStock.text = "В наличии: ${it.stock} шт."

            // Загружаем изображение с помощью Glide
            val imagePath = arguments?.getString("image_path")
            imagePath?.let {
                Glide.with(this)
                    .load(it)  // Это может быть путь к изображению или URL
                    .placeholder(R.color.black)
                    .error(R.color.black)
                    .into(binding.productImage)
            }

            // Кнопка "Добавить в корзину"
            binding.addToCartButton.setOnClickListener {
                // Логика добавления в корзину
                Toast.makeText(requireContext(), "Добавлено в корзину", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
