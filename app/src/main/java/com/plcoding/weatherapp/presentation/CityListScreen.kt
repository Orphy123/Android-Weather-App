package com.plcoding.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.weatherapp.R
import com.plcoding.weatherapp.presentation.ui.theme.DeepBlue

@Composable
fun CityListScreen(
    cities: List<String>,
    onNavigateBack: () -> Unit,
    viewModel: WeatherViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pic1),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            IconButton(onClick = onNavigateBack, modifier = Modifier.padding(16.dp)) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Go back", tint = Color.White)
            }
            LazyColumn {
                items(cities) { city ->
                    CityItem(
                        city = city,
                        onClick = {
                            onNavigateBack()
                            viewModel.loadWeatherInfoByCity(city)
                        },
                        onDelete = {
                            viewModel.removeCity(city)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun CityItem(city: String, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = city,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete city", tint = Color.Red)
            }
        }
    }
}


