package com.arcadone.cloudsharesnap.presentation.homescreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadone.cloudsharesnap.domain.GetCountriesUseCase
import com.arcadone.cloudsharesnap.domain.model.Country
import com.arcadone.cloudsharesnap.domain.model.onFailure
import com.arcadone.cloudsharesnap.domain.model.onSuccess
import com.arcadone.cloudsharesnap.networkconnection.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("Exception: ${exception.stackTrace}", Log.ERROR)
    }

    private val _countries = mutableStateOf<List<Country>>(emptyList())
    val countries: State<List<Country>> = _countries

    private val _selectedCountryIndex = mutableIntStateOf(-1)
    val selectedCountryIndex: State<Int> = _selectedCountryIndex

    fun setCountrySelection(selectedIndex: Int) {
        _selectedCountryIndex.intValue = selectedIndex
    }

    private val _uiState by lazy { mutableStateOf<HomeUiState>(HomeUiState.Loading(networkConnection.isOnline())) }
    val uiState: State<HomeUiState> by lazy { _uiState.apply { getCountries() } }

    fun getCountries(): Job {
        _uiState.value = HomeUiState.Loading(networkConnection.isOnline())
        return viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            getCountriesUseCase.invoke(Unit)
                .onSuccess {
                    _countries.value = it
                    _uiState.value = HomeUiState.ShowSuccess(Unit)
                }
                .onFailure {
                    _uiState.value = HomeUiState.ShowError(it.message ?: "An Error Occurred")
                }
        }
    }
}