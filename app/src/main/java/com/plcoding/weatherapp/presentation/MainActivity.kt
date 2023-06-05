package com.plcoding.weatherapp.presentation

import android.Manifest
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.plcoding.weatherapp.R
import com.plcoding.weatherapp.presentation.ui.theme.DarkBlue
import com.plcoding.weatherapp.presentation.ui.theme.DeepBlue
import com.plcoding.weatherapp.presentation.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {
            WeatherAppTheme {
                val showCityListScreen = remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        if (!showCityListScreen.value) {
                            TopAppBar(
                                title = { Text(text = "Weather App", color = Color.White) },
                                actions = {
                                    IconButton(onClick = { showCityListScreen.value = true }) {
                                        Icon(Icons.Filled.List, contentDescription = "City list", tint = Color.White)
                                    }
                                },
                                backgroundColor = DeepBlue
                            )
                        }
                    }
                ) {
                    if (!showCityListScreen.value) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Add FullScreenVideoView as background
                            AndroidView(factory = { context ->
                                FullScreenVideoView(context).apply {
                                    setVideoURI(android.net.Uri.parse("android.resource://" + context.packageName + "/" + R.raw.vid3))
                                    start()
                                    setOnPreparedListener { mp -> mp.isLooping = true }
                                }
                            }, modifier = Modifier.fillMaxSize())

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                item {
                                    Spacer(modifier = Modifier.height(3.dp))

                                    CitySearchInput(onCitySearch = viewModel::loadWeatherInfoByCity)

                                    // Display city name
                                    viewModel.state.weatherInfo?.let { weatherInfo ->
                                        weatherInfo.currentWeatherData?.let { currentWeatherData ->
                                            Text(
                                                text = viewModel.cityName.value,
                                                color = Color.White,
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp)
                                            )
                                        }
                                    }

                                    WeatherCard(
                                        state = viewModel.state,
                                        backgroundColor = DeepBlue
                                    )

                                    WeatherForecast(state = viewModel.state)
                                }
                            }
                            if (viewModel.state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            viewModel.state.error?.let { error ->
                                Text(
                                    text = error,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    } else {
                        CityListScreen(
                            cities = viewModel.searchedCities,
                            onNavigateBack = { showCityListScreen.value = false },
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
