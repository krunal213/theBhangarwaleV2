package com.app.thebhangarwale.address.entity

import android.location.Address
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AddressV2(val id : Long) : Address(Locale.ENGLISH)