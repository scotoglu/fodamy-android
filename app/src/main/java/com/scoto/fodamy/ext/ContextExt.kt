package com.scoto.fodamy.ext

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.scoto.fodamy.R

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.hideSoftKeyboard(view: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showSoftKeyboard(view: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}

fun Context.colorStateList(@ColorRes colorId: Int): ColorStateList =
    ColorStateList.valueOf(ContextCompat.getColor(this, colorId))

fun Context.getColorBy(@ColorRes colorId: Int): Int =
    ContextCompat.getColor(this, colorId)

fun Context.isDeviceHasConnection(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

fun Context.showPermissionDialog(
    @StringRes message: Int = R.string.dialog_message,
    positive: () -> Unit
): AlertDialog {
    return AlertDialog.Builder(this)
        .setTitle(R.string.dialog_title)
        .setMessage(message)
        .setPositiveButton(R.string.dialog_positive_btn) { dialog, _ ->
            positive.invoke()
            dialog.dismiss()
        }
        .show()
}
