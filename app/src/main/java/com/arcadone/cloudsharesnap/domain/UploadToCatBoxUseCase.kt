package com.arcadone.cloudsharesnap.domain

import android.content.Context
import android.net.Uri
import com.arcadone.cloudsharesnap.domain.model.ApiResult
import com.arcadone.cloudsharesnap.rest.CatBoxAPIRepository
import com.arcadone.cloudsharesnap.rest.RestUseCase
import javax.inject.Inject

interface UploadToCatBoxUseCase {
    data class Args(
        val uri: Uri,
        val progressCallback: (Int) -> Unit
    )

    suspend fun invoke(params: Args): ApiResult<String>
}

class UploadToCatBoxUseCaseImpl @Inject constructor(
    private val catBoxAPIRepository: CatBoxAPIRepository,
    private val context: Context
) : RestUseCase<UploadToCatBoxUseCase.Args, String>(), UploadToCatBoxUseCase {

    override suspend fun execute(params: UploadToCatBoxUseCase.Args): ApiResult<String> {
        val urlResult = catBoxAPIRepository.uploadFileToCatBox(
            context = context,
            uri = params.uri,
            progressCallback = params.progressCallback
        )
        return ApiResult.success(urlResult ?: "URL was Empty")
    }
}
