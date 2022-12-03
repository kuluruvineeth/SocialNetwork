package com.kuluruvineeth.socialnetwork.core.domain.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun ContentResolver.getFileName(uri: Uri):String{
    var returnCursor = query(uri,null,null,null,null)
    val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor?.moveToFirst()
    val fileName = nameIndex?.let { returnCursor?.getString(it) }
    returnCursor?.close()
    return fileName!!
}