package com.example.authors.signup.model

import com.example.authors.signup.SignUpApiService
import com.example.authors.signup.SignUpRequest
import com.example.authors.signup.SignUpResponse
import retrofit2.Response

class SignUpRepository(private val signUpApiService: SignUpApiService) {

    suspend fun signUp( phoneNumber: String, password: String,): Response<SignUpResponse> {
        val signUpRequest = SignUpRequest(phoneNumber, password)
        return signUpApiService.signUp(signUpRequest.toString(), )
    }
}
