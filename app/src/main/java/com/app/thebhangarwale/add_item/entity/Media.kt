package com.app.thebhangarwale.add_item.entity

import android.net.Uri

sealed class Media<out T : Uri>{
    data class Image<out T : Uri>(val data : T) : Media<T>()
    data class Video<out T : Uri>(val data : T) : Media<T>()
}
