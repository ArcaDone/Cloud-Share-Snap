package com.arcadone.cloudsharesnap.presentation.loadphotoscreen

import android.net.Uri
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.compose.ComposeHeader
import com.arcadone.cloudsharesnap.compose.LoadedPhotos
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import timber.log.Timber

@Composable
fun LoadPhotoScreen(
    navHostController: NavHostController,
    uriList: List<Uri>,
    viewModel: LoadPhotoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val progressMap by viewModel.uploadProgressMap.collectAsState()
    val resultMap by viewModel.resultMap.collectAsState()

    val onClickNavigateUp: () -> Unit = {
        navHostController.navigateUp()
    }

    LaunchedEffect(uriList) {
        Timber.d("URIS: $uriList")
        uriList.forEach { uri ->
            viewModel.loadPhoto(uri)
        }
    }

    Scaffold(
        containerColor = CloudShareSnap.colors.color01,
        topBar = {
            ComposeHeader(
                title = stringResource(R.string.photo_result),
                icon = R.drawable.ic_arrow_back,
                onBack = onClickNavigateUp
            )
        },
        content = {
            LoadedPhotos(it, uriList, progressMap, resultMap, context)
        }
    )
}
