package com.scoto.fodamy.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.ActivityMainBinding
import com.scoto.fodamy.ext.isDeviceHasConnection
import com.scoto.fodamy.ext.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        // back to normal theme
        setTheme(R.style.Theme_Fodamy)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)

        navigationSetup()
        navControllerListener()

        if (!isDeviceHasConnection()) {
            showMessage(R.string.no_connection)
        }
        setupDialog()
    }

    private fun setupDialog() {
        dialog = Dialog(this)
        dialog?.apply {
            setContentView(R.layout.progress_custom_dialog)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    fun showDialog() {
        if (dialog == null) {
            setupDialog()
        }
        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
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
        binding.bottomNavigationView.apply {
            setOnApplyWindowInsetsListener(null)
            setupWithNavController(navController)
        }
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
                    R.id.commentsFragment,
                    R.id.recipeDetailsFragment,
                    R.id.addRecipeFragment,
                    R.id.publishDraftFragment -> {
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
