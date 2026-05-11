package com.skyyeoh.composeweatherforcastapp.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyyeoh.composeweatherforcastapp.model.Favorite
import com.skyyeoh.composeweatherforcastapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDBRepository) :
    ViewModel() {

    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    private val _insertResult = MutableSharedFlow<Boolean>()
    val insertResult = _insertResult.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFavorite ->
                    if (listOfFavorite.isEmpty()) {
                        _favoriteList.value = emptyList()
                    } else {
                        _favoriteList.value = listOfFavorite
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(favorite)
                _insertResult.emit(true)
        }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorite)
        }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorite)
        }

    fun deleteAllFavorites() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
        }

    fun getFavoriteById(city: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavoriteById(city)
        }

}