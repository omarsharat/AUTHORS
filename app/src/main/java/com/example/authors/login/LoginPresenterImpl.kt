package com.example.authors.login

import com.example.authors.login.model.LoginRequestBody
import com.example.authors.login.model.LoginResponse

class LoginPresenterImpl(private val view: LoginContract.View) : LoginContract.Presenter {

    private val apiService: LoginApiService = retrofit.create(LoginApiService::class.java)

    override suspend fun performLogin(phoneNumber: String, password: String) {
        // Display loading progress
        view.showLoading()

        // Create the LoginRequestBody
        val loginRequestBody = LoginRequestBody(
            username = phoneNumber,
            password = password
        )

        // Make the login API call using Retrofit
        try {
            val response = apiService.login(langId =1)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                // Check for null response body
                if (loginResponse != null) {
                    // Login successful, pass the token to the view
                    view.onLoginSuccess(loginResponse.errMessage)
                } else {
                    view.onLoginError("Invalid response")
                }
            } else {
                // Login failed, handle the error
                view.onLoginError(response.message())
            }
        } catch (e: Exception) {
            // Handle network or API call exceptions
            view.onLoginError("An error occurred")
        } finally {
            // Hide loading progress
            view.hideLoading()
        }
    }
}
