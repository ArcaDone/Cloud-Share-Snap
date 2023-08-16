package com.arcadone.cloudsharesnap.misc

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import com.arcadone.cloudsharesnap.R

fun getDebugImageUris(context: Context): List<Uri> {
    val debugImageUrisArray = context.resources.getStringArray(R.array.debug_image_uris)
    return debugImageUrisArray.map { Uri.parse(it) }
}

fun copyToClipboard(text: String, context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Label", text)
    clipboard.setPrimaryClip(clip)
}

fun hasReadStoragePermission(context: Context): Boolean {
    val permission = Manifest.permission.READ_EXTERNAL_STORAGE
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}
