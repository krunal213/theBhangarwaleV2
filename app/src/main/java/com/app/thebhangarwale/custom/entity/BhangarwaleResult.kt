package com.app.thebhangarwale.custom.entity

sealed class BhangarwaleResult<out T : Any>{
    data class Success<out T : Any>(val data : T) : BhangarwaleResult<T>()
    data class Error(val exception: Exception) : BhangarwaleResult<Nothing>()
    object Loading : BhangarwaleResult<Nothing>()
}
