package com.example.sparta_wb.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sparta_wb.R
import com.example.sparta_wb.databinding.FragmentHomeBinding
import com.example.sparta_wb.ui.home.adapter.CardAdapter

class CardFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        productViewModel.loadProducts()
    }

    private fun setupRecyclerView() {
        val sharedPreferences = requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val isSubscribed = sharedPreferences.getBoolean("is_subscribed", false)

        adapter = CardAdapter(emptyList(), this, isSubscribed)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CardFragment.adapter
        }
    }

    private fun setupObservers() {
        productViewModel.products.observe(viewLifecycleOwner) { products ->
            adapter.updateOriginalList(products)
            adapter.updateList(products)
        }

        productViewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }

        productViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        productViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
