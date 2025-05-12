package com.example.sparta_wb.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sparta_wb.R
import com.example.sparta_wb.data.remote.api.RetrofitClient
import com.example.sparta_wb.data.remote.model.user.signIn.SigninRequest
import com.example.sparta_wb.databinding.FragmentLoginBinding
import com.example.sparta_wb.utils.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogIn.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = binding.etLogIn.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Все поля должны быть заполнены")
            return
        }

        lifecycleScope.launch {
            try {
                Log.d("LoginFragment", "Запрос отправлен на сервер: $email")
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.signin(SigninRequest(email, password)).execute()
                }

                if (response.isSuccessful) {
                    val signinResponse = response.body()
                    signinResponse?.let {
                        // Сохраняем токен
                        TokenManager.saveAccessToken(requireContext(), it.accessToken)
                        Log.d("LoginFragment", "Токен сохранен: ${it.accessToken}")

                        // Переходим на главный экран
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("LoginFragment", "Ошибка: $errorMessage")
                    showToast("Ошибка входа: ${errorMessage ?: "Неизвестная ошибка"}")
                }
            } catch (e: IOException) {
                Log.e("LoginFragment", "Ошибка сети: ${e.message}", e)
                showToast("Ошибка сети. Проверьте соединение с интернетом")
            } catch (e: HttpException) {
                Log.e("LoginFragment", "Ошибка сервера: ${e.message}", e)
                showToast("Ошибка сервера: ${e.message}")
            } catch (e: Exception) {
                Log.e("LoginFragment", "Неожиданная ошибка: ${e.message}", e)
                showToast("Произошла ошибка: ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}