package com.arcadone.cloudsharesnap.compose

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme
import com.arcadone.cloudsharesnap.R

@Composable
fun ComposeHeader(
    title: String,
    @DrawableRes icon: Int? = null,
    onBack: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CloudShareSnap.colors.color10,
            titleContentColor = CloudShareSnap.colors.color12,
            navigationIconContentColor = CloudShareSnap.colors.color12,
            actionIconContentColor = CloudShareSnap.colors.color12
        ),
        title = {
            Text(
                title,
                style = CloudShareSnap.typography.text01,
                color = CloudShareSnap.colors.color12,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        },
        navigationIcon = {
            if (icon != null) {
                IconButton(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(start = 16.dp),
                    onClick = {
                        focusManager.clearFocus()
                        onBack()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = CloudShareSnap.colors.color12,
                    )
                }
            }

        },
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun ComposeHeaderPreview() {
    CloudShareSnapTheme {
        ComposeHeader("Title very long test ellipsis yeah ok", R.drawable.ic_close_icon) {}
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ComposeHeaderDarkPreview() {
    CloudShareSnapTheme {
        ComposeHeader("Title very long test ellipsis yeah ok", R.drawable.ic_arrow_back) {}
    }
}
