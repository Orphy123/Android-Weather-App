package com.plcoding.weatherapp.presentation
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

@Composable
fun CitySearchInput(onCitySearch: (String) -> Unit) {
    val (cityName, setCityName) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = cityName,
            onValueChange = { setCityName(it) },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .border(1.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp),
            placeholder = {
                Text(
                    text = "Search city...",
                    style = MaterialTheme.typography.body1
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.onSurface,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { onCitySearch(cityName) }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        IconButton(
            onClick = { onCitySearch(cityName) },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}
