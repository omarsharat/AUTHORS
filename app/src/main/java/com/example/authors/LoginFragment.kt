package com.example.authors

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val signUpButton = view.findViewById<Button>(R.id.sinup)
        signUpButton.setOnClickListener {
            if (!isUserRegistered()) {
                val fragment = SignupFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.nav_continer, fragment)?.commit()
            } else {
                Toast.makeText(requireContext(), "You are already signed in", Toast.LENGTH_SHORT).show()
            }
        }

        val phoneNumberEditText = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.phone_text)
        val passwordEditText = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.pass_text)
        val loginButton = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.logn)
        loginButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()

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

        return view
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
}
