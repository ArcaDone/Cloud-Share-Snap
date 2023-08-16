package com.arcadone.cloudsharesnap.presentation.homescreen

sealed class HomeUiState {
    data class Loading(val isConnected: Boolean = false) : HomeUiState()
    data class ShowSuccess(val countries: Unit) : HomeUiState()
    data class ShowError(val errorMessage: String) : HomeUiState()
}