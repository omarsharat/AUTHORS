package com.example.authors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.authors.login.view.LoginFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginFragment = LoginFragment()

        val callback = object : MainCallback {

            override fun navigateTo(fragment: Fragment) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_continer, fragment, "")
                    .commit()
            }

            override fun setTitle(title: String) {

            }

        }
      //  loginFragment.Callback = callback

        supportFragmentManager.beginTransaction()
            .add(R.id.nav_continer, loginFragment)
            .commit()
    }

}