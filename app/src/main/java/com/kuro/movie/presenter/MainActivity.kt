package com.kuro.movie.presenter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.kuro.movie.R
import com.kuro.movie.databinding.ActivityMainBinding
import com.kuro.movie.navigation.NavigateFlow
import com.kuro.movie.navigation.NavigationFlow
import com.kuro.movie.navigation.Navigator

class MainActivity : AppCompatActivity(), NavigationFlow {

    private var navigator: Navigator? = null

    private lateinit var binding: ActivityMainBinding

    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerPermissionLaunch()
        checkNotificationPermissions()
        setUpNavController()
    }

    private fun setUpNavController() {
        navigator = Navigator()
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController
        navigator?.setNavController(navController)

        binding.bottomBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isPreviousBackStackBottomBarDestinations =
                isVisibleBottomBarOrNavRail(navController.previousBackStackEntry?.destination)
            val isCurrentBackStackDetailBottomSheet =
                destination.id == R.id.detail_bottom_sheet_flow

            val isVisibleBottomBarOrNavRailWhenOpenBottomDetail =
                isPreviousBackStackBottomBarDestinations && isCurrentBackStackDetailBottomSheet

            val isVisibleBottomBarOrNavigationRail = isVisibleBottomBarOrNavRail(destination)

            binding.bottomBar.isVisible =
                isVisibleBottomBarOrNavigationRail || isVisibleBottomBarOrNavRailWhenOpenBottomDetail
        }
    }

    private fun isVisibleBottomBarOrNavRail(destination: NavDestination?): Boolean {
        if (destination == null) return false
        return when (destination.id) {
            R.id.homeFragment -> true
            R.id.exploreFragment -> true
            R.id.settingsFragment -> true
            R.id.upComingFragment -> true
            R.id.myLibraryFragment -> true
            else -> false
        }
    }

    private fun registerPermissionLaunch() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                return@registerForActivityResult
            }
        }
    }

    private fun checkNotificationPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                handlePermissionRequest(
                    permission = Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                handlePermissionRequest(
                    permission = Manifest.permission.SCHEDULE_EXACT_ALARM
                )
            }
        }
    }

    private fun handlePermissionRequest(
        permission: String,
        permissionExplanation: String = getString(R.string.it_is_necc_for_notification)
    ) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showPermissionSnackBar(
                permission = permission,
                permissionExplanation = permissionExplanation
            )
        }
        permissionLauncher.launch(permission)
    }

    private fun showPermissionSnackBar(
        permission: String,
        permissionExplanation: String
    ) {
        val permissionAction = getString(R.string.allow)
        Snackbar.make(binding.root, permissionExplanation, Snackbar.LENGTH_INDEFINITE)
            .setAction(permissionAction) { permissionLauncher.launch(permission) }
            .show()
    }

    override fun navigateToFlow(flow: NavigateFlow) {
        navigator?.navigateToFlow(flow)
    }

    override fun onDestroy() {
        navigator = null
        super.onDestroy()
    }
}