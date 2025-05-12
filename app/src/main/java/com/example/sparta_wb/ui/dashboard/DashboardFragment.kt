package com.example.sparta_wb.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sparta_wb.databinding.FragmentDashboardBinding
import com.example.sparta_wb.ui.dashboard.adapter.DashboardAdapter
import com.example.sparta_wb.data.remote.model.CartItem // используем правильную модель

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Используем корректную модель CartItem
    private val cartItems = listOf(
        CartItem(
            imageUrl = "https://via.placeholder.com/100x140",
            title = "Товар 1",
            author = "Автор 1",
            price = 299.99,
            stock = 2,
            description = "Описание товара 1"
        ),
        CartItem(
            imageUrl = "https://via.placeholder.com/100x140",
            title = "Товар 2",
            author = "Автор 2",
            price = 149.50,
            stock = 1,
            description = "Описание товара 2"
        ),
        CartItem(
            imageUrl = "https://via.placeholder.com/100x140",
            title = "Товар 3",
            author = "Автор 3",
            price = 99.00,
            stock = 3,
            description = "Описание товара 3"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = DashboardAdapter(cartItems)
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
