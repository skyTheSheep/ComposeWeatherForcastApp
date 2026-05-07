package com.skyyeoh.composeweatherforcastapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.bawp.jetweatherforecast.model.Weather
import com.bawp.jetweatherforecast.model.WeatherItem
import com.skyyeoh.composeweatherforcastapp.data.DataOrException
import com.skyyeoh.composeweatherforcastapp.navigation.WeatherScreens
import com.skyyeoh.composeweatherforcastapp.utils.formatDate
import com.skyyeoh.composeweatherforcastapp.utils.formatDecimals
import com.skyyeoh.composeweatherforcastapp.widgets.HumidityWindPressureRow
import com.skyyeoh.composeweatherforcastapp.widgets.SunriseSunsetRow
import com.skyyeoh.composeweatherforcastapp.widgets.WeatherAppBar
import com.skyyeoh.composeweatherforcastapp.widgets.WeatherDetailRow
import com.skyyeoh.composeweatherforcastapp.widgets.WeatherStateImage
import com.skyyeoh.composeweatherforcastapp.widgets.mockWeatherItemList

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel(),
               city: String?) {

    Log.d("MainScreen", "MainScreen: city = $city")

    val weatherData = produceState(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city ?: "Seattle")
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
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        MainContent(data = weather, modifier = Modifier.padding(top = it.calculateTopPadding()) )
    }
}

@Composable
fun MainContent(data: Weather?, modifier: Modifier = Modifier) {

    val imageUrl = "https://openweathermap.org/img/wn/10d.png" // the 10d is where the icon is
//    val imageUrl = "https://openweathermap.org/img/wn/${data?.list.first().weather.first().icon}.png"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
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
                    ?: ("[EC] 56 " + "°"), style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold)
                Text(
                    text = data?.list?.first()?.weather?.first()?.main ?: "Snow [EC]",
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = data?.list?.first())
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        SunriseSunsetRow(weather = data?.list?.first())

        Text(
            text = "This Week", style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(2.dp)) {
                items(items = data?.list ?: mockWeatherItemList()) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}