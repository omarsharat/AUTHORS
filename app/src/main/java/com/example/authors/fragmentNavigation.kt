package com.example.authors

import androidx.fragment.app.Fragment

interface fragmentNavigation {
    abstract val name: String?

    fun navigateFragment(fragment: Fragment, addToStack:Boolean)
}