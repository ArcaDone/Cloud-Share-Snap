package com.arcadone.cloudsharesnap.compose

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.domain.model.Country
import com.arcadone.cloudsharesnap.mock.Mocks
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme

@Composable
fun CountryCard(
    country: Country,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                CloudShareSnap.colors.color04
            else
                CloudShareSnap.colors.color03
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        border = if (isSelected) BorderStroke(
            2.dp,
            CloudShareSnap.colors.color05
        ) else null
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = country.name,
                style = CloudShareSnap.typography.text05Bold,
                color = CloudShareSnap.colors.color12
            )
            Text(
                text = stringResource(R.string.iso, country.iso),
                style = CloudShareSnap.typography.text02,
                color = CloudShareSnap.colors.color12
            )
            Text(
                text = stringResource(R.string.iso_alpha2, country.isoAlpha2),
                style = CloudShareSnap.typography.text02,
                color = CloudShareSnap.colors.color12
            )
            Text(
                text = stringResource(R.string.iso_alpha3, country.isoAlpha3),
                style = CloudShareSnap.typography.text02,
                color = CloudShareSnap.colors.color12
            )
            Text(
                text = stringResource(R.string.phone_prefix, country.phonePrefix),
                style = CloudShareSnap.typography.text02,
                color = CloudShareSnap.colors.color12
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun CountryCardPreview() {
    CloudShareSnapTheme {
        CountryCard(
            country = Mocks.countryList.first(),
            isSelected = false,
            onClick = {
            })
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun CountryCardPreviewSelected() {
    CloudShareSnapTheme {
        CountryCard(
            country = Mocks.countryList.first(),
            isSelected = true,
            onClick = {
            })
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun CountryCardDarkPreview() {
    CloudShareSnapTheme {
        CountryCard(
            country = Mocks.countryList.first(),
            isSelected = false,
            onClick = {
            })
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun CountryCardDarkPreviewSelected() {
    CloudShareSnapTheme {
        CountryCard(
            country = Mocks.countryList.first(),
            isSelected = true,
            onClick = {
            })
    }
}