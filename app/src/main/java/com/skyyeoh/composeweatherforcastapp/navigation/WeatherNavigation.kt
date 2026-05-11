package com.skyyeoh.composeweatherforcastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skyyeoh.composeweatherforcastapp.screens.about.AboutScreen
import com.skyyeoh.composeweatherforcastapp.screens.favorite.FavoriteScreen
import com.skyyeoh.composeweatherforcastapp.screens.main.MainScreen
import com.skyyeoh.composeweatherforcastapp.screens.main.MainViewModel
import com.skyyeoh.composeweatherforcastapp.screens.search.SearchScreen
import com.skyyeoh.composeweatherforcastapp.screens.settings.SettingsScreen
import com.skyyeoh.composeweatherforcastapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController)
        }

        // just like www.google.com/cityname="Seattle"
        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )) { navBack ->
            navBack.arguments?.getString("city").let { city ->

                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController, mainViewModel,
                    city = city)
            }
        }
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController)
        }
    }
}