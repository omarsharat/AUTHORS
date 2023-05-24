package com.example.authors.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun onLoginSuccess(token: String)
        fun onLoginError(errorMessage: String)
    }

    interface Presenter {

        suspend fun performLogin(phoneNumber: String, password: String)

    }
}
