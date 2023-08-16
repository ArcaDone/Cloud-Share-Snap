package com.arcadone.cloudsharesnap.compose

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.misc.copyToClipboard
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap


@Composable
fun LoadedPhotos(
    it: PaddingValues,
    uriList: List<Uri>,
    progressMap: Map<Uri, Int>,
    resultMap: Map<Uri, String>,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(it)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 40.dp)
        ) {
            items(uriList) { uri ->
                val currentProgress = progressMap[uri] ?: 0
                val isLoading = currentProgress < 100
                val result = resultMap[uri] ?: ""

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
                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color.Gray.copy(alpha = 0.8f))
                            ) {
                                CircularProgressIndicator(
                                    trackColor = CloudShareSnap.colors.color09,
                                    color = CloudShareSnap.colors.color10,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.Center),
                                    progress = currentProgress / 100f
                                )
                            }

                        } else {
                            TextButton(
                                colors = ButtonDefaults.textButtonColors(containerColor = CloudShareSnap.colors.color12),
                                onClick = {
                                    copyToClipboard(text = result, context = context)

                                    Toast.makeText(
                                        /* context = */ context,
                                        /* text = */ "Link Copied :-)",
                                        /* duration = */ Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(R.string.copy_link),
                                    style = CloudShareSnap.typography.text04,
                                    color = CloudShareSnap.colors.color01,
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
}