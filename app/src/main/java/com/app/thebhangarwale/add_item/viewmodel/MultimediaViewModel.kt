package com.app.thebhangarwale.add_item.viewmodel

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel

class MultimediaViewModel(application: Application) : AndroidViewModel(application) {

    val photoUri: Uri?
        get() {
            return getApplication<Application>().contentResolver.insert(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                },
                ContentValues().apply {
                    put(
                        MediaStore.Images.Media.DISPLAY_NAME,
                        System.currentTimeMillis()
                    )
                }
            )
        }

    val videoUri: Uri?
        get() {
            return getApplication<Application>().contentResolver.insert(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                },
                ContentValues().apply {
                    put(
                        MediaStore.Video.Media.DISPLAY_NAME,
                        System.currentTimeMillis()
                    )
                }
            )
        }
}