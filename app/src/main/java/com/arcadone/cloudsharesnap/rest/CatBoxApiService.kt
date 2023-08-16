package com.arcadone.cloudsharesnap.rest

import retrofit2.http.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface CatBoxApiService {

    @Multipart
    @POST("user/api.php")
    suspend fun uploadFile(
        @Part("reqtype") reqtype: RequestBody,
        @Part fileToUpload: MultipartBody.Part
    ): Response<ResponseBody>
}
