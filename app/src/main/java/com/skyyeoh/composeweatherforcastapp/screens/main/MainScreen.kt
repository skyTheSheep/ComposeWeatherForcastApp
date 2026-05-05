package com.skyyeoh.composeweatherforcastapp.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.bawp.jetweatherforecast.model.Weather
import com.skyyeoh.composeweatherforcastapp.data.DataOrException
import com.skyyeoh.composeweatherforcastapp.utils.formatDate
import com.skyyeoh.composeweatherforcastapp.utils.formatDecimals
import com.skyyeoh.composeweatherforcastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = "Seattle")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {

    }

    MainScaffold(weatherData.data, navController)
}

@Composable
fun MainScaffold(weather: Weather?, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = (weather?.city?.name
                ?: "SkyLand") + ",${weather?.city?.country ?: "Sky World"}",
            navController = navController,
            icon = Icons.Default.ArrowBackIosNew
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        MainContent(data = weather, modifier = Modifier.padding(it))
    }
}

@Composable
fun MainContent(data: Weather?, modifier: Modifier = Modifier) {

    val imageUrl = "https://openweathermap.org/img/wn/10d.png" // the 10d is where the icon is
//    val imageUrl = "https://openweathermap.org/img/wn/${data?.list.first().weather.first().icon}.png"

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = data?.list?.first()?.dt?.let { formatDate(it) } ?: "Tue, May 5 (EMPTY CASE)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp))

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = data?.list?.first()?.temp?.day?.let { formatDecimals(it) + "°" }
                    ?: ("[EMPTY CASE] 56 " + "°"), style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold)
                Text(
                    text = data?.list?.first()?.weather?.first()?.main ?: "Snow [EMPTY CASE]",
                    fontStyle = FontStyle.Italic
                )
            }
        }


    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl, contentDescription = "Weather State Image",
        modifier = Modifier.size(80.dp),
        placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
        error = painterResource(id = android.R.drawable.ic_dialog_alert)
    )
}