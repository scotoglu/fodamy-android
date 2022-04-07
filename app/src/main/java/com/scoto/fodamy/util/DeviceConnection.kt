package com.scoto.fodamy.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.03.2022 at 12:23
 */
class DeviceConnection @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun isHasConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.M -> {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(network) ?: return false
                when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                val networkInfo = connectivityManager.activeNetworkInfo ?: return false
                networkInfo.isConnected
            }
        }
    }
}
