package com.farmershop.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.provider.Settings
import com.farmershop.R
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import java.util.*
import kotlin.coroutines.coroutineContext

object Utility {
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


}
interface LocationDialogCLickListner{
    fun onSettingClick()
    fun onDismissClick(dialog: DialogInterface)
}