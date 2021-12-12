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

        //back to normal theme
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

        //prevents the adding bottom margin to bottomNavigationView after changing fullscreen to normal screen
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun navControllerListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    navAndToolbarVisibility(false)
                }
                R.id.registerFragment -> {
                    navAndToolbarVisibility(false)
                }
                R.id.resetPasswordFragment -> {
                    navAndToolbarVisibility(false)
                }
                R.id.walkThroughFragment -> {
                    navAndToolbarVisibility(false)
                }
                R.id.splashFragment -> {
                    navAndToolbarVisibility(false)
                }
                R.id.imagePopupFragment -> {
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

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        /*
        *
        * There is two scenario:
        * the goal: https://www.youtube.com/watch?v=Covu3fPA1nQ
        * navigation component release 2.4.0-alpha01
        * Scenario 1 :
        *              if we set the  app:popUpTo="@id/splashFragment or WalkThroughFragment" and popUpToInclusive="true"
        *              bottom navigation multiple back stack doesn't work properly. When navigate to between fragment using bottomNav fragments doesn't save the state
        *
        * Scenario 2 :
        *              if we set the  app:popUpTo="@id/splashFragment or WalkThroughFragment" app bottomNav working properly but back button is not finish the app
        *              when user homeFragment(checked by currentBackStackEntry. Also previousBackStackEntry is SplashFragment or WalkthoroughFragment)
        *              to handle app finishing problem simply I wrote below code.
        * */

        //TODO("App not finished using back button")
        if (navController.currentBackStackEntry?.destination?.id == R.id.homeFragment &&
            navController.previousBackStackEntry?.destination?.id == R.id.splashFragment
            || navController.previousBackStackEntry?.destination?.id == R.id.walkThroughFragment
        ) {

            finish()
        }
        super.onBackPressed()
    }

}