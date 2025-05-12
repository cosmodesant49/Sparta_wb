package com.example.sparta_wb.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sparta_wb.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var subscriptionSwitch: Switch
    private lateinit var statusTextView: TextView

    // Для доступа к SharedPreferences
    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
    }

    private val subscriptionKey = "is_subscribed"

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        subscriptionSwitch = binding.subscriptionSwitch
        statusTextView = binding.subscriptionStatusTextView

        // Загружаем состояние подписки из SharedPreferences
        val isSubscribed = sharedPreferences.getBoolean(subscriptionKey, false)
        subscriptionSwitch.isChecked = isSubscribed
        updateSubscriptionStatus(isSubscribed)

        // Обработчик изменения состояния Switch
        subscriptionSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Сохраняем состояние подписки
            sharedPreferences.edit().putBoolean(subscriptionKey, isChecked).apply()
            // Обновляем статус подписки
            updateSubscriptionStatus(isChecked)
        }

        return root
    }

    private fun updateSubscriptionStatus(isSubscribed: Boolean) {
        // Обновляем текст в зависимости от состояния подписки
        if (isSubscribed) {
            statusTextView.text = "Подписка активирована. Спасибо за покупку!"
        } else {
            statusTextView.text = "Подписка не активирована. Включите подписку для доступа."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
