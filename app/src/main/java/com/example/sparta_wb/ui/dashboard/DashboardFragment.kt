// com/example/sparta_wb/ui/dashboard/DashboardFragment.kt
package com.example.sparta_wb.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sparta_wb.databinding.FragmentDashboardBinding
import com.example.sparta_wb.ui.dashboard.adapter.DashboardAdapter
import com.example.sparta_wb.data.remote.model.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.example.sparta_wb.data.remote.api.RetrofitClient
import com.example.sparta_wb.utils.TokenManager

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DashboardAdapter
    private val orders = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DashboardAdapter(orders)
        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCart.adapter = adapter

        fetchOrders()
    }

    private fun fetchOrders() {
        val token = TokenManager.getAccessToken(requireContext())
        if (token.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show()
            return
        }


        RetrofitClient.instance.getMyOrders("Bearer $token").enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        orders.clear()
                        orders.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch orders", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}