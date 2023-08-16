package com.arcadone.cloudsharesnap.presentation.photoselectionscreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.arcadone.cloudsharesnap.networkconnection.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoSelectionViewModel @Inject constructor(
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val _images = mutableStateOf<List<Uri>>(emptyList())
    val images: State<List<Uri>> = _images

    fun isOnline() = networkConnection.isOnline()
    fun selectedImages(imageUriList: List<Uri>) {
        _images.value = _images.value.toMutableList().apply {
            addAll(imageUriList)
        }
    }

    fun removeImage(uriToRemove: Uri) {
        _images.value = _images.value.filter { selectedImageUri-> selectedImageUri != uriToRemove }

    }
}