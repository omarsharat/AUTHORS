package com.example.authors

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SignupFragment : Fragment() {


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Navigate to the home fragment (login fragment)
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnMale: MaterialButton
    private lateinit var btnFemale: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        sharedPreferences =
            requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(view.findViewById(R.id.toolbar))
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val buttonSignup = view.findViewById<Button>(R.id.sinup)
        btnMale = view.findViewById(R.id.male)
        btnFemale = view.findViewById(R.id.female)

        btnMale.setOnClickListener {
            onMaleButtonClick()
        }

        btnFemale.setOnClickListener {
            onFemaleButtonClick()
        }

        // Set initial state
        onMaleButtonClick()
        buttonSignup.setOnClickListener {
            val firstNameEditText =
                view.findViewById<TextInputEditText>(R.id.fname)
            val lastNameEditText =
                view.findViewById<TextInputEditText>(R.id.lname)
            val phoneNumberEditText =
                view.findViewById<TextInputEditText>(R.id.phnum)
            val passwordEditText =
                view.findViewById<TextInputEditText>(R.id.passcode)

            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()
            val gender = if (btnMale.isSelected) "Male" else "Female"

            if (isInputValid(firstName, lastName, phoneNumber, password, gender)) {
                if (isValidPhoneNumber(phoneNumber)) {
                    saveUser(firstName, lastName, phoneNumber, password, gender)
                    Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT)
                        .show()
                    navigateToLoginFragment()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Phone number is not in the correct format",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please fill all the required fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    private fun onMaleButtonClick() {
        btnMale.isSelected = true
        btnMale.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green))
        btnMale.setTextColor(resources.getColor(R.color.button_text_white_color))

        btnFemale.isSelected = false
        btnFemale.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        btnFemale.setTextColor(resources.getColor(R.color.green))
    }

    private fun onFemaleButtonClick() {
        btnMale.isSelected = false
        btnMale.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        btnMale.setTextColor(resources.getColor(R.color.green))

        btnFemale.isSelected = true
        btnFemale.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green))
        btnFemale.setTextColor(resources.getColor(R.color.button_text_white_color))
    }

    private fun isInputValid(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        gender: String
    ): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank() && password.isNotBlank() && gender.isNotBlank()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberRegex = Regex("^07\\d{8}\$")
        return phoneNumber.matches(phoneNumberRegex)
    }

    private fun saveUser(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
        gender: String
    ) {
        val identifier = phoneNumber + password
        val editor = sharedPreferences.edit()
        editor.putString("$identifier.firstName", firstName)
        editor.putString("$identifier.lastName", lastName)
        editor.putString("$identifier.phoneNumber", phoneNumber)
        editor.putString("$identifier.password", password)
        editor.putString("$identifier.gender", gender)
        editor.apply()
    }

   private fun navigateToLoginFragment() {
       findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
    }
}
