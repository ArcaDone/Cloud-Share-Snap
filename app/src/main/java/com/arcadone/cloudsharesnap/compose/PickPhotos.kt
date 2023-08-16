package com.arcadone.cloudsharesnap.compose

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.misc.getDebugImageUris
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme

@Composable
fun PickPhotos(
    padding: PaddingValues,
    selectedImageUris: List<Uri>,
    iconResId: Int,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onImageRemoved: (Uri) -> Unit = {},
    onClickAddMore: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 40.dp)
        ) {
            items(selectedImageUris) { uri ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Red,
                                contentColor = CloudShareSnap.colors.color08
                            ),
                            onClick = {
                                onImageRemoved(uri)
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .size(24.dp)
                                .clip(CircleShape)
                        ) {
                            Icon(
                                painter = painterResource(id = iconResId),
                                contentDescription = null,
                                modifier = iconModifier
                            )
                        }
                    }
                }
            }
            if (selectedImageUris.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { onClickAddMore.invoke() },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(CloudShareSnap.colors.color05)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.add_a_photo),
                                contentDescription = null,
                                tint = CloudShareSnap.colors.color12,
                                modifier = Modifier
                                    .padding(8.dp)
                            )
                            Text(
                                text = stringResource(R.string.add_more),
                                modifier = Modifier.padding(8.dp),
                                style = CloudShareSnap.typography.text02,
                                color = CloudShareSnap.colors.color12,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

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
private fun PickPhotosPreview() {
    val context = LocalContext.current

    CloudShareSnapTheme {
        PickPhotos(
            padding = PaddingValues(20.dp),
            selectedImageUris = getDebugImageUris(context = context),
            iconResId = R.drawable.remove
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun PickPhotosDarkPreview() {
    val context = LocalContext.current

    CloudShareSnapTheme {
        PickPhotos(
            padding = PaddingValues(20.dp),
            selectedImageUris = getDebugImageUris(context = context),
            iconResId = R.drawable.remove
        )
    }
}