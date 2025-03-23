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
                Log.d("RegistrationFragment", "запрос отправлен на бек $email")
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.signin(SigninRequest(email, password)).execute()
                }

                if (response.isSuccessful) {
                    Log.d(
                        "RegistrationFragment",
                        "успешно ${response.body()?.message}"
                    )
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Log.e("RegistrationFragment", "Ошибка $errorMessage")
                    showToast("Ошибка $errorMessage")
                }
            } catch (e: IOException) {
                Log.e("RegistrationFragment", "Ошибка сети ${e.message}", e)
                showToast("Ошибка сети. Проверьте соединение с интернетом")
            } catch (e: HttpException) {
                Log.e("RegistrationFragment", "Ошибка бекенда ${e.message}", e)
                showToast("Ошибка бекенда ${e.message}")
            } catch (e: Exception) {
                Log.e("RegistrationFragment", "Ошибка ${e.message}", e)
                showToast("Произошла ошибка")
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