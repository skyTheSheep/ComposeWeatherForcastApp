package com.skyyeoh.composeweatherforcastapp.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.skyyeoh.composeweatherforcastapp.R
import com.skyyeoh.composeweatherforcastapp.model.FeelsLike
import com.skyyeoh.composeweatherforcastapp.model.Temp
import com.skyyeoh.composeweatherforcastapp.model.WeatherItem
import com.skyyeoh.composeweatherforcastapp.model.WeatherObject
import com.skyyeoh.composeweatherforcastapp.utils.formatDate
import com.skyyeoh.composeweatherforcastapp.utils.formatDateTime
import com.skyyeoh.composeweatherforcastapp.utils.formatDecimals

@Composable
fun SunriseSunsetRow(weather: WeatherItem?) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(text = weather?.sunrise?.let { formatDateTime(it) } ?: "6:00 AM",
                style = MaterialTheme.typography.labelSmall)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = weather?.sunset?.let { formatDateTime(it) } ?: "8:00 PM",
                style = MaterialTheme.typography.labelSmall)
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem?) {

    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather?.humidity}%",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather?.pressure} psi",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather?.humidity} mph",
                style = MaterialTheme.typography.labelSmall
            )
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


@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/10d.png" // the 10d is where the icon is
//    val imageUrl = "https://openweathermap.org/img/wn/${data?.list.first().weather.first().icon}.png"

    Surface(modifier = Modifier.padding(4.dp).fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = formatDate(weather.dt)
                .split(",").first(), modifier = Modifier.padding(start = 6.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier
                    .padding(4.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = weather.weather.first().description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp))
            }

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = SemiBold
                )) {
                    append(formatDecimals(weather.temp.max) + "°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )) {
                    append(formatDecimals(weather.temp.min) + "°")
                }
            })
        }

    }
}

fun mockWeatherItemList(): List<WeatherItem> {
    return listOf(
        WeatherItem(
            clouds = 90,
            deg = 180,
            dt = 123456789,
            feels_like = FeelsLike(day = 55.0, night = 54.0, eve = 56.0, morn = 51.0),
            gust = 0.0,
            humidity = 80,
            temp = Temp(day = 56.0, min = 50.0, max = 60.0, night = 55.0, eve = 57.0, morn = 52.0),
            pressure = 1013,
            weather = listOf(
                WeatherObject(
                    description = "light rain",
                    icon = "10d",
                    id = 500,
                    main = "Rain"
                )
            ),
            speed = 5.0,
            sunrise = 123456789,
            sunset = 123456789,
            pop = 0.2,
            rain = 0.0,
        ),
        WeatherItem(
            clouds = 90,
            deg = 180,
            dt = 123456789,
            feels_like = FeelsLike(day = 55.0, night = 54.0, eve = 56.0, morn = 51.0),
            gust = 0.0,
            humidity = 80,
            temp = Temp(day = 56.0, min = 50.0, max = 60.0, night = 55.0, eve = 57.0, morn = 52.0),
            pressure = 1013,
            weather = listOf(
                WeatherObject(
                    description = "light rain",
                    icon = "10d",
                    id = 500,
                    main = "Rain"
                )
            ),
            speed = 5.0,
            sunrise = 123456789,
            sunset = 123456789,
            pop = 0.2,
            rain = 0.0,
        ),
        WeatherItem(
            clouds = 90,
            deg = 180,
            dt = 123456789,
            feels_like = FeelsLike(day = 55.0, night = 54.0, eve = 56.0, morn = 51.0),
            gust = 0.0,
            humidity = 80,
            temp = Temp(day = 56.0, min = 50.0, max = 60.0, night = 55.0, eve = 57.0, morn = 52.0),
            pressure = 1013,
            weather = listOf(
                WeatherObject(
                    description = "light rain",
                    icon = "10d",
                    id = 500,
                    main = "Rain"
                )
            ),
            speed = 5.0,
            sunrise = 123456789,
            sunset = 123456789,
            pop = 0.2,
            rain = 0.0,
        ),
        WeatherItem(
            clouds = 90,
            deg = 180,
            dt = 123456789,
            feels_like = FeelsLike(day = 55.0, night = 54.0, eve = 56.0, morn = 51.0),
            gust = 0.0,
            humidity = 80,
            temp = Temp(day = 56.0, min = 50.0, max = 60.0, night = 55.0, eve = 57.0, morn = 52.0),
            pressure = 1013,
            weather = listOf(
                WeatherObject(
                    description = "light rain",
                    icon = "10d",
                    id = 500,
                    main = "Rain"
                )
            ),
            speed = 5.0,
            sunrise = 123456789,
            sunset = 123456789,
            pop = 0.2,
            rain = 0.0,
        ),
        WeatherItem(
            clouds = 90,
            deg = 180,
            dt = 123456789,
            feels_like = FeelsLike(day = 55.0, night = 54.0, eve = 56.0, morn = 51.0),
            gust = 0.0,
            humidity = 80,
            temp = Temp(day = 56.0, min = 50.0, max = 60.0, night = 55.0, eve = 57.0, morn = 52.0),
            pressure = 1013,
            weather = listOf(
                WeatherObject(
                    description = "light rain",
                    icon = "10d",
                    id = 500,
                    main = "Rain"
                )
            ),
            speed = 5.0,
            sunrise = 123456789,
            sunset = 123456789,
            pop = 0.2,
            rain = 0.0,
        )
    )
}