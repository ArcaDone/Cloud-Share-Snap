package com.arcadone.cloudsharesnap.presentation.photoselectionscreen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arcadone.cloudsharesnap.R
import com.arcadone.cloudsharesnap.compose.CenteredTextWithIcon
import com.arcadone.cloudsharesnap.compose.ComposeHeader
import com.arcadone.cloudsharesnap.compose.PickPhotos
import com.arcadone.cloudsharesnap.misc.hasReadStoragePermission
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.Shapes
import timber.log.Timber

@Composable
fun PhotoSelectionScreen(
    navHostController: NavHostController,
    onClickLoadPhoto: (List<Uri>) -> Unit,
    viewModel: PhotoSelectionViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            viewModel.selectedImages(uris)
        }
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.d("Permission granted! Yeah")

            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            Timber.w("Permission denied! :-(")
            Toast.makeText(
                /* context = */ context,
                /* text = */ "Permission denied, please check app settings",
                /* duration = */ Toast.LENGTH_SHORT
            ).show()
        }
    }

    val onClickNavigateUp: () -> Unit = {
        navHostController.navigateUp()
    }

    Scaffold(
        containerColor = CloudShareSnap.colors.color01,
        topBar = {
            ComposeHeader(
                title = stringResource(R.string.photo_selection),
                icon = R.drawable.ic_arrow_back,
                onBack = onClickNavigateUp
            )
        },
        content = {
            if (viewModel.images.value.isEmpty()) {
                CenteredTextWithIcon(
                    text = stringResource(R.string.tap_to_add_photos),
                    padding = it,
                    iconResId = R.drawable.add_a_photo,
                    modifier = Modifier.fillMaxSize(),
                    iconModifier = Modifier.size(96.dp),
                    onClick = {
                        if (hasReadStoragePermission(context)) {
                            multiplePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                )
            } else {
                PickPhotos(
                    padding = it,
                    selectedImageUris = viewModel.images.value,
                    iconResId = R.drawable.remove,
                    onImageRemoved = { uriToRemove ->
                        viewModel.removeImage(uriToRemove)
                    },
                    onClickAddMore = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }

        },
        bottomBar = {
            Button(
                onClick = {
                    if (viewModel.images.value.isNotEmpty() && viewModel.isOnline()) {
                        onClickLoadPhoto.invoke(viewModel.images.value)
                    } else {
                        Toast.makeText(
                            /* context = */ context,
                            /* text = */ "It seems you are offline, please check your connection",
                            /* duration = */ Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = viewModel.images.value.isNotEmpty(),
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
                Text(text = stringResource(R.string.load_photos_to_cloud))
            }
        }
    )
}

