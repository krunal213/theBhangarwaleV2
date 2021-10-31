package com.app.thebhangarwale.home.create_request.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Request(
    val id: Long,
    val product: String,
    val quantity: String,
    val price : Float
) : Parcelable