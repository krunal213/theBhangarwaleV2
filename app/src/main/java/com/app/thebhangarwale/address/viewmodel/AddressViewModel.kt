package com.app.thebhangarwale.address.viewmodel

import android.app.Application
import android.location.Address
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.app.thebhangarwale.address.entity.AddressV2
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Error
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.random.Random

class AddressViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    fun getAddress() = liveData<BhangarwaleResult<ArrayList<AddressV2>>> {
        try {
            //18.98307494631155,72.83906176686287
            val myaddress = ArrayList<AddressV2>()
            val address = AddressV2( 0)
            address.setAddressLine(
                0,
                "4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India"
            )
            address.latitude = 18.98307494631155
            address.longitude = 72.83906176686287
            myaddress.add(address)

            val addressOne = AddressV2( 1)
            addressOne.setAddressLine(
                0,
                "4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India"
            )
            addressOne.latitude = 18.98307494631155
            addressOne.longitude = 72.83906176686287
            myaddress.add(addressOne)

            val addressTwo = AddressV2( 2)
            addressTwo.setAddressLine(
                0,
                "4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India"
            )
            addressTwo.latitude = 18.98307494631155
            addressTwo.longitude = 72.83906176686287
            myaddress.add(addressTwo)

            val addressThree = AddressV2( 3)
            addressThree.setAddressLine(
                0,
                "4D, Harish Arjun Palay Marg, New Patra Chawl, Dhaku Prabhuchi Wadi, Byculla East, Byculla, Mumbai, Maharashtra 400033, India"
            )
            addressThree.latitude = 18.98307494631155
            addressThree.longitude = 72.83906176686287
            myaddress.add(addressThree)

            emit(Success(myaddress))

        }catch (ex : Exception){
            emit(Error(ex))
        }
    }

    fun submitAddress()  = liveData<BhangarwaleResult<String>> {
        try {
            emit(BhangarwaleResult.Loading)
            delay(5000)
            emit(Success("Address added successfully."))
        }catch (ex : Exception){

        }
    }

    fun updateAddress()  = liveData<BhangarwaleResult<String>> {
        try {
            emit(BhangarwaleResult.Loading)
            delay(5000)
            emit(Success("Address updated successfully."))
        }catch (ex : Exception){

        }
    }

}