package com.example.sparta_wb.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sparta_wb.R
import com.example.sparta_wb.data.remote.api.RetrofitClient
import com.example.sparta_wb.data.remote.model.user.liqid.LiquidityRequest
import com.example.sparta_wb.data.remote.model.user.liqid.LiquidityResponse
import com.example.sparta_wb.databinding.FragmentNotificationsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    private val subscriptionKey = "is_subscribed"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val isSubscribed = sharedPreferences.getBoolean(subscriptionKey, false)
        binding.subscriptionSwitch.isChecked = isSubscribed
        updateSubscriptionStatus(isSubscribed)

        binding.subscriptionSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(subscriptionKey, isChecked).apply()
            updateSubscriptionStatus(isChecked)
        }

        binding.predictButton.setOnClickListener {
            val price = binding.priceInput.text.toString().toDoubleOrNull()
            val rating = binding.ratingInput.text.toString().toDoubleOrNull()
            val votes = binding.votesInput.text.toString().toIntOrNull()

            if (price != null && rating != null && votes != null) {
                val request = LiquidityRequest(rating, votes, price)
                RetrofitClient.instance.getLiquidityPrediction(request)
                    .enqueue(object : Callback<LiquidityResponse> {
                        override fun onResponse(
                            call: Call<LiquidityResponse>,
                            response: Response<LiquidityResponse>
                        ) {
                            if (response.isSuccessful) {
                                val liquidityResponse = response.body()
                                liquidityResponse?.let {
                                    binding.predictionResult.text =
                                        "Рейтинг ликвидности: ${it.status} (Score: ${it.score})"
                                }
                            } else {
                                Toast.makeText(requireContext(), "Ошибка при получении данных", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<LiquidityResponse>, t: Throwable) {
                            Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
                        }
                    })
            } else {
                Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun updateSubscriptionStatus(isSubscribed: Boolean) {
        if (isSubscribed) {
            binding.subscriptionStatusTextView.text = "Подписка активирована. Спасибо за покупку!"
            binding.title.visibility = View.VISIBLE
            binding.priceInput.visibility = View.VISIBLE
            binding.ratingInput.visibility = View.VISIBLE
            binding.votesInput.visibility = View.VISIBLE
            binding.predictButton.visibility = View.VISIBLE
            binding.predictionResult.visibility = View.VISIBLE
        } else {
            binding.subscriptionStatusTextView.text = "Подписка не активирована. Включите подписку для доступа."
            binding.title.visibility = View.GONE
            binding.priceInput.visibility = View.GONE
            binding.ratingInput.visibility = View.GONE
            binding.votesInput.visibility = View.GONE
            binding.predictButton.visibility = View.GONE
            binding.predictionResult.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
