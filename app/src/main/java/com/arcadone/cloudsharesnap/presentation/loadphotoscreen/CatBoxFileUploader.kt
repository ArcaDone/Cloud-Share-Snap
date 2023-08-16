package com.arcadone.cloudsharesnap.presentation.loadphotoscreen

import com.arcadone.cloudsharesnap.rest.CatBoxApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface FileUploader {
    suspend fun uploadFile(
        reqType: RequestBody,
        filePart: MultipartBody.Part,
        progressCallback: (Int) -> Unit
    ): String?
}

class CatBoxFileUploader(private val catBoxApiService: CatBoxApiService) : FileUploader {
    override suspend fun uploadFile(
        reqType: RequestBody,
        filePart: MultipartBody.Part,
        progressCallback: (Int) -> Unit
    ): String? {
        val response: Response<ResponseBody> = catBoxApiService.uploadFile(reqType, filePart)
        return response.body()?.string()
    }
}