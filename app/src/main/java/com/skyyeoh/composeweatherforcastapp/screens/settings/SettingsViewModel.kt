package com.skyyeoh.composeweatherforcastapp.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyyeoh.composeweatherforcastapp.model.Unit
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
class SettingsViewModel @Inject constructor(private val repository: WeatherDBRepository) :
    ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    private val _insertResult = MutableSharedFlow<Boolean>()
    val insertResult = _insertResult.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnit ->
                    if (listOfUnit.isEmpty()) {
                        _unitList.value = emptyList()
                    } else {
                        _unitList.value = listOfUnit
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUnit(unit)
            _insertResult.emit(true)
        }

    fun updateUnit(unit: Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUnit(unit)
        }

    fun deleteUnit(unit: Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUnit(unit)
        }

    fun deleteAllUnits() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUnits()
        }

}