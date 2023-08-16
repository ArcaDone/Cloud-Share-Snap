package com.arcadone.cloudsharesnap.presentation.loadphotoscreen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcadone.cloudsharesnap.domain.UploadToCatBoxUseCase
import com.arcadone.cloudsharesnap.domain.UploadToCatBoxUseCaseImpl
import com.arcadone.cloudsharesnap.domain.model.onFailure
import com.arcadone.cloudsharesnap.domain.model.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoadPhotoViewModel @Inject constructor(
    private val uploadImageToCatBoxUseCase: UploadToCatBoxUseCaseImpl
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.d("Exception: ${exception.stackTrace}", Log.ERROR)
    }

    private val _uploadProgressMap = MutableStateFlow<Map<Uri, Int>>(emptyMap())
    val uploadProgressMap: StateFlow<Map<Uri, Int>> = _uploadProgressMap

    private val _resultMap = MutableStateFlow<Map<Uri, String>>(emptyMap())
    val resultMap: StateFlow<Map<Uri, String>> = _resultMap

    fun loadPhoto(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val updatedMap = _uploadProgressMap.value.toMutableMap()
            updatedMap[uri] = 0
            _uploadProgressMap.value = updatedMap

            uploadImageToCatBoxUseCase.invoke(UploadToCatBoxUseCase.Args(
                uri
            ) { progress ->
                val progressMap = _uploadProgressMap.value.toMutableMap()
                progressMap[uri] = progress
                _uploadProgressMap.value = progressMap
            })
                .onSuccess { result ->

                    val resultUpdateMap = _resultMap.value.toMutableMap()
                    resultUpdateMap[uri] = result
                    _resultMap.value = resultUpdateMap

                    val completedProgressMap = _uploadProgressMap.value.toMutableMap()
                    completedProgressMap[uri] = 100
                    _uploadProgressMap.value = completedProgressMap

                    Timber.d("Result link: $result")
                }
                .onFailure {
                    Timber.e("An error occurred: ${it.message} || ${it.stackTrace}")
                }
        }
    }
}

