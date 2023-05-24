package com.example.authors.signup

import com.example.authors.signup.view.SignupFragment

interface SignUpContract {
    interface View {
        fun showSignUpSuccess(message: String)
        fun showSignUpError( errorMessage: String?)
        fun showLoading()
        fun hideLoading()
        abstract fun SignUpPresenter(signupFragment: SignupFragment): SignUpPresenter
    }

    interface Presenter {


        fun signUp( phoneNumber: String, password: String)
        fun cancelSignUp()
    }

}