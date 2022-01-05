package com.scoto.fodamy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        // back to normal theme
        setTheme(R.style.Theme_Fodamy)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationSetup()
        navControllerListener()
    }

    private fun navigationSetup() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favoritesFragment,
                R.id.loginFragment
            )
        )

        // prevents the adding bottom margin to bottomNavigationView after changing fullscreen to normal screen
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun navControllerListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible =
                when (destination.id) {
                    R.id.loginFragment,
                    R.id.registerFragment,
                    R.id.resetPasswordFragment,
                    R.id.walkThroughFragment,
                    R.id.splashFragment,
                    R.id.imagePopupFragment,
                    R.id.commentsFragment -> {
                        false
                    }
                    else -> {
                        true
                    }
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}
