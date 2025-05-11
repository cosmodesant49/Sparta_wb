package com.example.sparta_wb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sparta_wb.R
import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val product = arguments?.getSerializable("product") as? Product

        if (product != null) {
            binding.productTitle.text = product.title
            binding.productDescription.text = product.description
            binding.productPrice.text = "Цена: ${product.price}$"
            binding.productStock.text = "В наличии: ${product.stock} шт."
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
