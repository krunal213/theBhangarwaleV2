package com.app.thebhangarwale.custom.entity

sealed class BhangarwaleResult<out T>{
    data class Success<out T>(val data : T) : BhangarwaleResult<T>()
    data class Error(val exception: Exception) : BhangarwaleResult<Nothing>()
    object Loading : BhangarwaleResult<Nothing>()
}
