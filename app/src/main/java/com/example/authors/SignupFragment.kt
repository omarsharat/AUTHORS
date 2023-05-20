package com.example.authors
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SignupFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        val buttonSignup = view.findViewById<Button>(R.id.sinup)
        buttonSignup.setOnClickListener {
            val firstNameEditText = view.findViewById<TextInputEditText>(R.id.fname)
            val lastNameEditText = view.findViewById<TextInputEditText>(R.id.lname)
            val phoneNumberEditText = view.findViewById<TextInputEditText>(R.id.phnum)
            val passwordEditText = view.findViewById<TextInputEditText>(R.id.passcode)
            val genderRadioGroup = view.findViewById<RadioGroup>(R.id.R_gender)

            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()
            val gender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.male -> "male"
                R.id.fe_male -> "female"
                else -> ""
            }

            if (isInputValid(firstName, lastName, phoneNumber, password, gender)) {
                if (isValidPhoneNumber(phoneNumber)) {
                    saveUser(firstName, lastName, phoneNumber, password, gender)
                    Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT).show()
                    navigateToLoginFragment()
                } else {
                    Toast.makeText(requireContext(), "Phone number is not in the correct format", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all the required fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun isInputValid(firstName: String, lastName: String, phoneNumber: String, password: String, gender: String): Boolean {
        return firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank() && password.isNotBlank() && gender.isNotBlank()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberRegex = Regex("^079[0-9]{7}\$")
        return phoneNumber.matches(phoneNumberRegex)
    }

    private fun saveUser(firstName: String, lastName: String, phoneNumber: String, password: String, gender: String) {
        val editor = sharedPreferences.edit()
        editor.putString("firstName", firstName)
        editor.putString("lastName", lastName)
        editor.putString("phoneNumber", phoneNumber)
        editor.putString("password", password)
        editor.putString("gender", gender)
        editor.apply()
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
    }
}
