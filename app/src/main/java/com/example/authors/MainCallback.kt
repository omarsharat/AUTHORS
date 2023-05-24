package com.example.authors

import androidx.fragment.app.Fragment

interface MainCallback {

    fun navigateTo(fragment: Fragment)
    fun setTitle(title: String)
}