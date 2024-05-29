package com.capstone.hicare.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

const val MAX_SIZE = 1_000_000

fun uriToFile(imageUri: Uri, context: Context): File {
    return File(context.cacheDir, "${System.currentTimeMillis()}_temp.jpg")
}

fun createTempFile(context: Context): File {
    val directory = context.externalCacheDir
    return File.createTempFile( ".jpg", directory.toString())
}

fun File.reduceFileImage(): File {
    var compressQuality = 100
    val bitmap = BitmapFactory.decodeFile(path)
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAX_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(this))
    return this
}