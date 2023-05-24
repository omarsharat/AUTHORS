package com.example.authors.login

interface LoginPresenter {
    suspend fun performLogin(phoneNumber: String, password: String)
}
