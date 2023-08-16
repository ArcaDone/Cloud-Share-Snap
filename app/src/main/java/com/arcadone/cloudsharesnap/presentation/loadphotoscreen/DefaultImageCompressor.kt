package com.arcadone.cloudsharesnap.presentation.loadphotoscreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

interface ImageCompressor {
    fun compressImage(originalBytes: ByteArray): ByteArray
}

class DefaultImageCompressor : ImageCompressor {
    override fun compressImage(originalBytes: ByteArray): ByteArray {
        val bitmap = BitmapFactory.decodeByteArray(originalBytes, 0, originalBytes.size)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }
}
