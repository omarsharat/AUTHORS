package com.example.authors.signup

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignUpPresenter(
    private val view: SignUpContract.View,
    private val signUpRepository: SignUpApiService
) : SignUpContract.Presenter, CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun signUp( phoneNumber: String, password: String) {
        view.showLoading()
        launch {
            val response = withContext(Dispatchers.IO) {
                signUpRepository.signUp(phoneNumber)
            }
            if (response.isSuccessful) {
                val signUpResponse = response.body()
                // Handle the successful sign-up response
                signUpResponse?.let {
                    view.hideLoading()
                    view.showSignUpSuccess("")
                }
            } else {
                val errorCode = response.code()
                val errorBody = response.errorBody()?.string()
                // Handle the sign-up error
                view.hideLoading()
                view.showSignUpError(errorBody ?: "Unknown error occurred")
            }
        }
    }

    override fun cancelSignUp() {
        job.cancel()
    }
}
