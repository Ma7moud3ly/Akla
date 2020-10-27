package com.ma7moud3ly.akla.util

import android.content.Context
import android.net.ConnectivityManager
//check whether the device connected to the internet
object CheckInternet {
    fun isConnected(c: Context): Boolean {
        return try {
            val connectivityManager =
                c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        } catch (e: Exception) {
            false
        }
    }
}
