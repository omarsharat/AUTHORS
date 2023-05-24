package com.example.authors.signup

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface SignUpApiService {
    @GET("fact")
    suspend fun signUp(@Body signUpRequest: String, phonenumber: String = " "): Response<SignUpResponse>
}
