package com.example.authors.login.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.authors.R
import com.example.authors.databinding.FragmentLoginBinding
import com.example.authors.login.LoginContract
import com.example.authors.login.LoginPresenterImpl
import com.example.authors.login.model.LoginRequestBody
import com.example.authors.login.model.LoginResponse
import com.example.authors.login.retrofit
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment(), LoginContract.View {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenterImpl(this)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val signUpButton = binding.sinup
        signUpButton.setOnClickListener {
            if (!isUserRegistered()) {
                navigateToSignupFragment()
            } else {
                Toast.makeText(requireContext(), "You are already signed in", Toast.LENGTH_SHORT).show()
            }
        }

        val phoneNumberEditText = binding.phoneText
        val passwordEditText = binding.passText
        val loginButton = binding.logn
        loginButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()
            lifecycleScope.launch {
                presenter.performLogin(phoneNumber, password)
            }
            if (phoneNumber.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a phone number", Toast.LENGTH_SHORT).show()
            } else if (password.length < 8 || password.length > 16) {
                Toast.makeText(requireContext(), "Password must be between 8 and 16 characters", Toast.LENGTH_SHORT).show()
            } else {
                if (checkLoginCredentials(phoneNumber, password)) {
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    navigateToSignupFragment()
                } else {
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isUserRegistered(): Boolean {
        val phoneNumber = sharedPreferences.getString("phoneNumber", null)
        val password = sharedPreferences.getString("password", null)
        return !phoneNumber.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    private fun checkLoginCredentials(phoneNumber: String, password: String): Boolean {
        val identifier = phoneNumber + password
        val savedPhoneNumber = sharedPreferences.getString("$identifier.phoneNumber", null)
        val savedPassword = sharedPreferences.getString("$identifier.password", null)
        return phoneNumber == savedPhoneNumber && password == savedPassword
    }

    private fun navigateToSignupFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }

    override fun showLoading() {
        // Show loading progress
        // You can implement the loading progress based on your UI design
    }

    override fun hideLoading() {
        // Hide loading progress
    }

    override fun onLoginSuccess(token: String) {
        // Handle successful login
    }

    override fun onLoginError(errorMessage: String) {
        // Handle login error
    }
}
