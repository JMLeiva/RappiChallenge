package com.jml.rappichallenge.tools

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

import javax.inject.Inject
import javax.inject.Singleton

/* Utility to check internet connection state*/
@Singleton
class ConnectionManager @Inject constructor(application: Application) {

    private val connectivityManager: ConnectivityManager?

    //Default if ConectivityService is not available for any reason
    val isInternetConnected: Boolean
        get() {
            if (connectivityManager == null) {
                return true
            }

            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    init {
        connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}