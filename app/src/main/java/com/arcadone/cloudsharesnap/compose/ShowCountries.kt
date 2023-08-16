package com.arcadone.cloudsharesnap.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.domain.model.Country
import com.arcadone.cloudsharesnap.mock.Mocks
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme

@Composable
fun ShowCountries(
    countries: List<Country>,
    selectedCountryIndex: Int,
    onClickCountry: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    stringResource(R.string.click_to_choose_yours).uppercase(),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    style = CloudShareSnap.typography.text02,
                    color = CloudShareSnap.colors.color12,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            items(countries) { country ->
                val isSelected = selectedCountryIndex == countries.indexOf(country)
                CountryCard(country = country, isSelected = isSelected) {
                    onClickCountry.invoke(countries.indexOf(country))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun ShowCountriesPreview() {

    CloudShareSnapTheme {
        ShowCountries(
            countries = Mocks.countryList,
            selectedCountryIndex = 1,
            onClickCountry = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ShowCountriesDarkPreview() {

    CloudShareSnapTheme {
        ShowCountries(
            countries = Mocks.countryList,
            selectedCountryIndex = 1,
            onClickCountry = {}
        )
    }
}