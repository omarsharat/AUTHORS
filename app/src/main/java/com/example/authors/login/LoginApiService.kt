package com.example.authors.login




import com.example.authors.login.model.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiService {
    @GET("entries")
    suspend fun login(@Query("langId") langId: Int): Response<LoginResponse>
}


