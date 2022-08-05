package com.farmershop.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.farmershop.R
import com.farmershop.ui.activity.auth.Login
import com.farmershop.utils.AppSession
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class SplashActivity : AppCompatActivity() {
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    var isLocationOn = false

    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_splash)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        /* activityScope.launch {
             delay(2000)
             startActivity(Intent(this@SpalshActivity, HomeActivity::class.java))
             finish()
         }

 */
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1111
            )
        }
    }

    override fun onResume() {
        super.onResume()
        /*if (isLocationOn) {
            getLastLocation()
            isLocationOn = false
        }*/
    }

    override fun onPause() {
        super.onPause()
        activityScope.cancel()
    }

    private fun getLastLocation() {
       // if (Utility.isLocationEnabled(this)) {
         /*   if (!Validation.isEmpty(AppSession.getInstance(this).getCurrAddress().toString())) {
                Handler().postDelayed({
                    // Toast.makeText(this@SplashActivity, lastLocation?.latitude.toString(), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Home::class.java))
                    finish()
                }, 2000)
            } else {*/
             /*   if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        lastLocation = task.result

                        AppSession.getInstance(this).setCurrLat(lastLocation?.latitude.toString())
                        AppSession.getInstance(this).setCurrLong(lastLocation?.longitude.toString())
                        AppSession.getInstance(this).setCurrAddress(
                            Utility.convertLatLngToAddress(
                                this,
                                LatLng(lastLocation?.latitude!!, lastLocation?.longitude!!)
                            )
                        )
                        Log.d("ssassasasaa", lastLocation?.latitude.toString())
                        Log.d("ssassasasaa", lastLocation?.longitude.toString())*/


                        //activityScope.launch {
                        //delay(2000)

                        //}

                        Handler().postDelayed({
                            if (AppSession.isLoggedIn()) {
                                startActivity(Intent(this, Home::class.java))
                            } else {
                                startActivity(Intent(this, Login::class.java))
                            }
                            // Toast.makeText(this@SplashActivity, lastLocation?.latitude.toString(), Toast.LENGTH_SHORT).show()
                            finish()
                        }, 2000)
                        //  latitudeText!!.text = latitudeLabel + ": " + (lastLocation)!!.latitude
                        //  longitudeText!!.text = longitudeLabel + ": " + (lastLocation)!!.longitude
                    /*} else {
                        Log.w(TAG, "getLastLocation:exception", task.exception)
                        Toast.makeText(
                            this,
                            "No location detected. Make sure location is enabled on the device.",
                            Toast.LENGTH_LONG
                        ).show()
                    }*/
             /*   }
            }*/
       /* } else {
            Utility.locationAlertDialogEnabled(this, "GPS in Settings",
                "GPS is not enabled. Do you want to go to settings menu?",
                "Settings", "Cancel",
                object : LocationDialogCLickListner {
                    override fun onSettingClick() {
                        isLocationOn = true
                        val intent = Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                        startActivity(intent)
                    }

                    override fun onDismissClick(dialog: DialogInterface) {
                        dialog.dismiss()
                        finish()
                    }

                })
        }*/
    }

    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(this, mainTextStringId, Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1111) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                finish()
            }
        } else {
            finish()
        }
    }
}