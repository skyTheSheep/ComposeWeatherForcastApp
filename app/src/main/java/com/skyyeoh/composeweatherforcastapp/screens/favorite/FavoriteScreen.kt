package com.skyyeoh.composeweatherforcastapp.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.skyyeoh.composeweatherforcastapp.model.Favorite
import com.skyyeoh.composeweatherforcastapp.navigation.WeatherScreens
import com.skyyeoh.composeweatherforcastapp.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorite Cities",
            icon = Icons.Default.ArrowBackIosNew,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = 6.dp,
                    start = 6.dp,
                    end = 6.dp
                )
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoriteViewModel.favoriteList.collectAsState().value

                LazyColumn {
                    items(items = list) { favorite ->
                        CityRow(favorite = favorite, navController = navController, favoriteViewModel = favoriteViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(favorite: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp))

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape, color = Color(0xFFD1E3E1)) {
                Text(text = favorite.country, modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.labelMedium)
            }

            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete icon",
                modifier = Modifier.clickable {
                    favoriteViewModel.deleteFavorite(favorite)
                },
                tint = Color.Red.copy(alpha = 0.3f))

        }
    }

}