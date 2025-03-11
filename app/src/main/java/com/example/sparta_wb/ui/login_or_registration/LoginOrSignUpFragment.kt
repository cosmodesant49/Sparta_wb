package com.example.sparta_wb.ui.login_or_registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sparta_wb.R
import com.example.sparta_wb.databinding.FragmentLoginrOrSignUpBinding

class LoginOrSignUpFragment : Fragment() {
    private val binding by lazy {
        FragmentLoginrOrSignUpBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_login_or_SignUpFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_or_SignUpFragment_to_registrationFragment)
        }
    }
}