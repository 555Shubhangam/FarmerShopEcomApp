package com.farmershop.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.*

object Utility {
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isLocationEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gps_enabled = false
            var network_enabled = false
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (ex: Exception) {
            }
            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (ex: Exception) {
            }
            if (gps_enabled && network_enabled) {
                true
            } else false
        } else {
            val mode = Settings.Secure.getInt(
                context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }
    fun  locationAlertDialogEnabled(context: Context,dialogTitle:String,dialogDesc:String,
                                    dialogBtnPstName:String,dialogBtnNgtName:String,
                                    listner: LocationDialogCLickListner){
        val alertDialog = AlertDialog.Builder(context)

        // Setting DialogHelp Title

        // Setting DialogHelp Title
        alertDialog.setTitle(dialogTitle)
        alertDialog.setCancelable(false)

        // Setting DialogHelp Message


        // Setting DialogHelp Message
        alertDialog
            .setMessage(dialogDesc)

        // On pressing Settings button

        // On pressing Settings button
        alertDialog.setPositiveButton(
            dialogBtnPstName
        ) { dialog, which ->
           /* djldls = true
            val intent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
            context.startActivity(intent)*/
            listner.onSettingClick()
        }

        // on pressing cancel button

        // on pressing cancel button
        alertDialog.setNegativeButton(
            dialogBtnNgtName
        ) { dialog, which ->
            //dialog.cancel()
            listner.onDismissClick(dialog)
        }

        // Showing Alert Message

        // Showing Alert Message
        alertDialog.show()
    }
    fun convertLatLngToAddress(context: Context,latLng:LatLng):String{
        val addresses: List<Address>
        val geocoder = Geocoder(context, Locale.getDefault())
        var address: String
        try {
            addresses = geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        } catch (e: IOException) {
            e.printStackTrace()
            address = "Error"
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            address = "Error"
        }
        return address
    }

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
//         else {
//            connectivityManager.activeNetworkInfo?.run {
//                return when(type) {
//                    ConnectivityManager.TYPE_WIFI -> true
//                    ConnectivityManager.TYPE_MOBILE -> true
//                    ConnectivityManager.TYPE_ETHERNET -> true
//                    else -> false
//                }
//            }
//        }
        return false
    }
    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
interface LocationDialogCLickListner{
    fun onSettingClick()
    fun onDismissClick(dialog: DialogInterface)
}