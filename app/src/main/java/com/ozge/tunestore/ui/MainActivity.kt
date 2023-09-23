package com.ozge.tunestore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ozge.tunestore.MainApplication
import com.ozge.tunestore.R
import com.ozge.tunestore.common.gone
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.common.visible
import com.ozge.tunestore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //MainApplication.provideRetrofit(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.splashFragment,
                R.id.signInFragment,
                R.id.signUpFragment,
                R.id.detailFragment,
                R.id.paymentFragment,
                R.id.paymentSuccessFragment -> {
                    binding.bottomNavigationView.gone()
                }

                else -> {
                    binding.bottomNavigationView.visible()
                }
            }
        }
    }


}