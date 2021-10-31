package com.app.thebhangarwale.home.create_request.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.app.thebhangarwale.custom.entity.BhangarwaleResult
import com.app.thebhangarwale.home.create_request.entity.Request
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Error
import com.app.thebhangarwale.custom.entity.BhangarwaleResult.Success
import kotlin.random.Random

class RequestViewModel(application: Application) : AndroidViewModel(application) {

    fun getRequests(): LiveData<BhangarwaleResult<ArrayList<Request>>> = liveData {
        try {
            emit(
                Success(
                    arrayListOf<Request>(
                        Request(
                           0,
                            "Loha (Rs.30/kg)",
                            "Quantity : 300 kg",
                            9000.00f
                        ),
                        Request(
                            1,
                            "Loha (Rs.30/kg)",
                            "Quantity : 300 kg",
                            9000.00f
                        ),
                        Request(
                            2,
                            "Loha (Rs.30/kg)",
                            "Quantity : 300 kg",
                            9000.00f
                        ),
                        Request(
                            3,
                            "Loha (Rs.30/kg)",
                            "Quantity : 300 kg",
                            9000.00f
                        ),
                        Request(
                            4,
                            "Loha (Rs.30/kg)",
                            "Quantity : 300 kg",
                            9000.00f
                        )
                    )
                )
            )
        } catch (ex: Exception) {

        }
    }

}