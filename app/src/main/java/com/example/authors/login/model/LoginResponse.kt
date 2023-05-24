package com.example.authors.login.model

data class LoginResponse(
    val count: Int,
    val entries: List<ApiEntry>,
    val description: String
) {
    val errMessage = "ERROR"
    val errMassege = "ERROR"
}

data class ApiEntry(
    val API: String,
    val Description: String,
    val Auth: String,
    val HTTPS: Boolean,
    val Cors: String,
    val Link: String,
    val Category: String
)
