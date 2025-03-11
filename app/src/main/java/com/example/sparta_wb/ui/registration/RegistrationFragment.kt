package com.example.sparta_wb.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sparta_wb.R
import com.example.sparta_wb.databinding.FragmentLoginBinding
import com.example.sparta_wb.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private val binding by lazy {
        FragmentRegistrationBinding.inflate(layoutInflater)
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
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_navigation_home)
        }
    }
}