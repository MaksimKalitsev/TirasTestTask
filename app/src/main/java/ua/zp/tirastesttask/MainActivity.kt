package ua.zp.tirastesttask

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import ua.zp.tirastesttask.presentation.Navigation
import ua.zp.tirastesttask.presentation.WeatherViewModel
import ua.zp.tirastesttask.presentation.ui.theme.TirasTestTaskTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val isLocationPermissionsGranted: Boolean
        get() = run {
            val permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            permissions.forEach {
                val res = checkCallingOrSelfPermission(it)
                if (res != PackageManager.PERMISSION_GRANTED) return@run false
            }
            return@run true
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val hasFineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val hasCoarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
            if (hasFineLocation && hasCoarseLocation) {
                viewModel.fetchWeatherForCurrentLocation()
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    showBackgroundLocationDialog()
                }
            } else {
                Toast.makeText(this, "No access permissions", Toast.LENGTH_SHORT).show()
            }
        }

        setContent {
            TirasTestTaskTheme {
                Navigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLocationPermissionsGranted) {
            viewModel.fetchWeatherForCurrentLocation()
        } else {
            requestBasicPermissions()
        }
    }

    private fun requestBasicPermissions() {
        val basicPermissionsList = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        permissionLauncher.launch(basicPermissionsList)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBackgroundPermission() {
        permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showBackgroundLocationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Background Location Permission")
            .setMessage("We need background location permission to provide weather updates.")
            .setPositiveButton("Allow") { dialog, _ ->
                dialog.dismiss()
                requestBackgroundPermission()
            }
            .setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }

}


