package com.arcadone.cloudsharesnap.rest

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.FileUploader
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.ImageCompressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

interface CatBoxAPIRepository {
    suspend fun uploadFileToCatBox(
        context: Context,
        uri: Uri,
        progressCallback: (Int) -> Unit
    ): String?
}

class CatBoxAPIRepositoryImpl @Inject constructor(
    private val imageCompressor: ImageCompressor,
    private val fileUploader: FileUploader
) : CatBoxAPIRepository {

    override suspend fun uploadFileToCatBox(
        context: Context,
        uri: Uri,
        progressCallback: (Int) -> Unit
    ): String? {
        val reqType = "fileupload".toRequestBody("text/plain".toMediaTypeOrNull())

        var inputStream: InputStream? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            val originalFileBytes = withContext(Dispatchers.IO) {
                inputStream?.readBytes()
            }
                ?: return null

            // Apply Image compression
            val compressedFileBytes = imageCompressor.compressImage(originalFileBytes)
            val totalFileSize = compressedFileBytes.size.toLong()

            val imageName = uri.lastPathSegment
            val extension = getExtensionFromUri(uri = uri, context = context)
            val completeFileName = "$imageName.$extension"

            var bytesUploaded: Long = 0
            val chunkSize = 256 // The chunk size for progressive upload

            val requestBody = object : RequestBody() {
                override fun contentType(): MediaType? = "multipart/form-data".toMediaTypeOrNull()

                override fun contentLength(): Long = totalFileSize

                override fun writeTo(sink: BufferedSink) {
                    Timber.d("totalFileSize: $totalFileSize, chunkSize: $chunkSize")
                    for (i in 0 until totalFileSize step chunkSize.toLong()) {
                        val endIndex = kotlin.math.min(i + chunkSize, totalFileSize)
                        val chunk = compressedFileBytes.copyOfRange(i.toInt(), endIndex.toInt())
                        sink.write(chunk)
                        bytesUploaded += chunk.size
                        val progress = (bytesUploaded * 100 / totalFileSize).toInt()

                        progressCallback(progress)

                    }
                }
            }

            val filePart = MultipartBody.Part.createFormData(
                name = "fileToUpload",
                filename = completeFileName,
                body = requestBody
            )

            return fileUploader.uploadFile(reqType, filePart, progressCallback)

        } catch (e: IOException) {
            Timber.e("Image URL error : ${e.message}")
            return null
        } finally {
            withContext(Dispatchers.IO) {
                inputStream?.close()
            }
        }
    }

    private fun getExtensionFromUri(uri: Uri, context: Context): String {
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            ?: "jpg" // Default to JPEG if no extension found
    }
}

