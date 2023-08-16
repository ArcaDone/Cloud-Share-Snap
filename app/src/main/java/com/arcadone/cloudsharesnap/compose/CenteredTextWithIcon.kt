package com.arcadone.cloudsharesnap.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme

@Composable
fun CenteredTextWithIcon(
    text: String,
    padding: PaddingValues,
    iconResId: Int,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .background(CloudShareSnap.colors.color01)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            tint = CloudShareSnap.colors.color12,
            modifier = iconModifier
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth(),
            style = CloudShareSnap.typography.text03Bold,
            color = CloudShareSnap.colors.color12,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun CenteredTextWithIconPreview() {
    CloudShareSnapTheme {
        CenteredTextWithIcon(
            text = stringResource(R.string.add_a_photos),
            padding = PaddingValues(20.dp),
            iconResId = R.drawable.add_a_photo,
            modifier = Modifier.fillMaxSize(),
            iconModifier = Modifier.size(96.dp)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun CenteredTextWithIconDarkPreview() {
    CloudShareSnapTheme {
        CenteredTextWithIcon(
            text = stringResource(R.string.click_me),
            padding = PaddingValues(20.dp),
            iconResId = R.drawable.add_a_photo,
            modifier = Modifier.fillMaxSize(),
            iconModifier = Modifier.size(96.dp)
        )
    }
}