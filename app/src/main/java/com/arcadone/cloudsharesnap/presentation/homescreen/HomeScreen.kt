package com.arcadone.cloudsharesnap.presentation.homescreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.compose.ComposeHeader
import com.arcadone.cloudsharesnap.compose.ShowCountries
import com.arcadone.cloudsharesnap.compose.ThreeBounceAnimation
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.Shapes
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToImageSelection: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState
    val countries = viewModel.countries.value
    val selectedCountryIndex = viewModel.selectedCountryIndex.value

    Scaffold(
        containerColor = CloudShareSnap.colors.color01,
        topBar = {
            ComposeHeader(
                title = stringResource(R.string.countries)
            )
        },
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                when (uiState) {
                    is HomeUiState.Loading -> {
                        ThreeBounceAnimation()
                    }

                    is HomeUiState.ShowSuccess -> {
                        ShowCountries(
                            countries = countries,
                            selectedCountryIndex = selectedCountryIndex,
                            onClickCountry = { countryIndex ->
                                viewModel.setCountrySelection(countryIndex)
                            }
                        )
                    }

                    is HomeUiState.ShowError -> {
                        ThreeBounceAnimation()
                        Timber.e("Error: ${(uiState as HomeUiState.ShowError).errorMessage}")
                        Toast.makeText(
                            /* context = */ context,
                            /* text = */ "It seems you are offline, please check your connection",
                            /* duration = */ Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        },
        bottomBar = {
            Button(
                onClick = {
                    if (selectedCountryIndex != -1) {
                        navigateToImageSelection.invoke()
                    }
                },
                enabled = selectedCountryIndex != -1,
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
                    .heightIn(min = 64.dp)
                    .fillMaxWidth(),
                shape = Shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CloudShareSnap.colors.color06,
                    disabledContainerColor = CloudShareSnap.colors.color07
                )
            ) {
                Text(stringResource(R.string.step_one_continue))
            }
        }
    )

}

