// com/example/sparta_wb/ui/details/DetailsFragment.kt
package com.example.sparta_wb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sparta_wb.R
import com.example.sparta_wb.data.remote.api.RetrofitClient
import com.example.sparta_wb.data.remote.model.OrderResponse
import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.data.remote.model.user.orders.CreateOrderRequest
import com.example.sparta_wb.data.remote.model.user.orders.OrderedProduct
import com.example.sparta_wb.data.remote.model.user.orders.ShippingAddress
import com.example.sparta_wb.databinding.FragmentDetailsBinding
import com.example.sparta_wb.utils.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentProduct: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        arguments?.getSerializable("product")?.let {
            currentProduct = it as Product
            bindProductData(currentProduct)
        }

        return binding.root
    }

    private fun bindProductData(product: Product) {
        binding.productTitle.text = product.title
        binding.productPrice.text = "Цена: ${product.price}$"
        binding.productStock.text = "В наличии: ${product.stock} шт."

        // Загружаем изображение
        product.images.firstOrNull()?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.color.black)
                .error(R.color.black)
                .into(binding.productImage)
        }

        // Кнопка "Добавить в корзину" (создание заказа)
        binding.addToCartButton.setOnClickListener {
            createOrder()
        }
    }

    private fun createOrder() {
        val token = TokenManager.getAccessToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, войдите в систему", Toast.LENGTH_SHORT).show()
            return
        }

        // Создаем тестовые данные для заказа
        val shippingAddress = ShippingAddress(
            phone = "98758675",
            name = "Имя пользователя",
            address = "Адрес доставки",
            city = "Город",
            postCode = "123456",
            state = "Область",
            country = "RU"
        )

        val orderedProduct = OrderedProduct(
            id = currentProduct.id,
            product_unit_price = currentProduct.price.toDouble(),
            product_quantity = 1 // По умолчанию 1 товар
        )

        val orderRequest = CreateOrderRequest(
            shippingAddress = shippingAddress,
            orderedProducts = listOf(orderedProduct)
        )

        RetrofitClient.instance.createOrder("Bearer $token", orderRequest)
            .enqueue(object : Callback<OrderResponse> {
                override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Заказ успешно создан",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Ошибка при создании заказа: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка сети: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}