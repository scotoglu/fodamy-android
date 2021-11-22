package com.scoto.fodamy.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.ActivityMainBinding
import com.scoto.fodamy.ext.toast
import com.scoto.fodamy.helper.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {

        //back to normal theme
        setTheme(R.style.Theme_Fodamy)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationSetup()

        toolbarEndIconCheckByAuth()

        navControllerListener()

        logoutStateObserver()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


    }

    private fun logoutStateObserver() {
        viewModel.state.observe(this, { state ->
            this.toast(state)
        })
        viewModel.navigateTo.observe(this, {
            navController.navigate(R.id.loginFragment2)
        })
    }

    private fun navigationSetup() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favoritesFragment,
                R.id.loginFragment2
            )
        )

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun navControllerListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment2 -> {
                    navAndToolbarVisibility(false)
                }
                R.id.registerFragment2 -> {
                    navAndToolbarVisibility(false)
                }
                R.id.resetPasswordFragment2 -> {
                    navAndToolbarVisibility(false)
                }
                R.id.walkThroughFragment -> {
                    navAndToolbarVisibility(false)
                }
                R.id.splashFragment -> {
                    navAndToolbarVisibility(false)
                }
                else -> {
                    navAndToolbarVisibility(true)
                }
            }
        }

    }

    private fun navAndToolbarVisibility(state: Boolean) {
        binding.apply {
            bottomNavigationView.isVisible = state
            appBarLayout.isVisible = state
        }
    }

    private fun toolbarEndIconCheckByAuth() {
        dataStoreManager.token.asLiveData().observe(this, { token ->
            val endIcon = if (token.isNullOrBlank()) R.drawable.ic_login else R.drawable.ic_logout_2
            binding.toolbarIvEndIcon.setImageResource(endIcon)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}