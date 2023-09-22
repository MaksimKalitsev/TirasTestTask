package ua.zp.tirastesttask

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.zp.tirastesttask.presentation.Navigation
import ua.zp.tirastesttask.presentation.WeatherViewModel
import ua.zp.tirastesttask.presentation.ui.theme.TirasTestTaskTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

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
            } else {
                Toast.makeText(this, "No access permissions", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            TirasTestTaskTheme {
                    Navigation()
            }
        }
    }
}


