package com.app.thebhangarwale.custom.network

import kotlin.Throws
import java.io.IOException
import com.app.thebhangarwale.custom.exception.NoInternetConnectionException
import android.net.ConnectivityManager
import android.annotation.SuppressLint
import android.content.Context
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response

class BhangarwaleNetworkConnectionInterceptor(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoInternetConnectionException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}