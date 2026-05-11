package com.skyyeoh.composeweatherforcastapp.widgets

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.skyyeoh.composeweatherforcastapp.model.Favorite
import com.skyyeoh.composeweatherforcastapp.navigation.WeatherScreens
import com.skyyeoh.composeweatherforcastapp.screens.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = { },
    onButtonClicked: () -> Unit = { }
) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value) {
        Log.d("WeatherAppBar", "WeatherAppBar: showDialog = ${showDialog.value}")
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search icon"
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                    Log.d("WeatherAppBar", "WeatherAppBar: showDialog = ${showDialog.value}")
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "more icon"
                    )
                }
            } else Box { }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if (isMainScreen) {
                val isAlreadyFavorite =
                    favoriteViewModel.favoriteList.collectAsState().value.filter { item ->
                        item.city == title.split(",").first()
                    }

                if (isAlreadyFavorite.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "favorite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val dataList = title.split(",")
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = dataList.first(),
                                        country = dataList[1],
                                    )
                                )

                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                } else {
                    Box {}
                }

                LaunchedEffect(Unit) {
                    favoriteViewModel.insertResult.collect {
                        showToast(context)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Gray,
        ), windowInsets = WindowInsets(0.dp)
    )
}


fun showToast(context: Context) {
    Toast.makeText(
        context, "Added to Favorites",
        Toast.LENGTH_LONG
    ).show()
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    val items = listOf("About", "Favorites", "Settings")


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = showDialog.value,
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {

            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
//                    showDialog.value = false
                }, text = {
                    Text(text = text, color = Color.Black, modifier = Modifier.clickable {
                        showDialog.value = false
                        navController.navigate(
                            when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavoriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name

                            }
                        )

                    })
                }, leadingIcon = {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings
                        },
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                })
            }

        }
    }
}
