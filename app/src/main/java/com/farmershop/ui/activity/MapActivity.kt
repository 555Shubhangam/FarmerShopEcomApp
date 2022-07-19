package com.farmershop.ui.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.farmershop.R
import com.farmershop.utils.AppSession
import com.farmershop.databinding.ActivityMapBinding
import com.farmershop.ui.activity.Home
import com.farmershop.utils.GlobalConstants
import com.farmershop.utils.LocationDialogCLickListner
import com.farmershop.utils.Utility
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnFailureListener

class MapActivity : AppCompatActivity() {
    private var mMap: GoogleMap? = null
    private var currentLat = ""
    private var currentLongi = ""
    private var address = ""
    private var locationType = ""
    lateinit var binding: ActivityMapBinding
    private var client: FusedLocationProviderClient? = null
    private var mapFragment: SupportMapFragment? = null
    private var mLatLong: LatLng? = null
    private var currentLatLong: LatLng? = null
    private var addresss: String? = null
    var mGoogleMap: GoogleMap? = null
    var isLocationOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_map)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        client = LocationServices.getFusedLocationProviderClient(this)

        locationType = intent.getStringExtra(GlobalConstants.LOCATION_TYPE_INTENT)!!
        if (locationType == "CurrentLoc") {
            currentLat = AppSession.getInstance(this).getCurrLat().toString()
            currentLongi = AppSession.getInstance(this).getCurrLong().toString()
            currentLatLong = LatLng(currentLat.toDouble(), currentLongi.toDouble())
            Log.d("ssassasasaaCurrLatLng", currentLatLong.toString())
        } else {
            address = intent.getStringExtra(GlobalConstants.ADDRESS_INTENT)!!
            currentLat = intent.getStringExtra(GlobalConstants.LAT_INTENT)!!
            currentLongi = intent.getStringExtra(GlobalConstants.LONG_INTENT)!!
            currentLatLong = LatLng(currentLat.toDouble(), currentLongi.toDouble())
            Log.d("ssassasasaa12Address", address)
            Log.d("ssassasasaa123LATLNG", currentLatLong.toString())
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                1111
            )
        }
        googleMapPinPoint()
        binding.submitBt.setOnClickListener {
          AppSession.getInstance(this).setCurrAddress(binding.address.text.toString())
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isLocationOn) {
            getCurrentLocation()
            isLocationOn = false
        }
    }


    private fun googleMapPinPoint() {
        mapFragment!!.getMapAsync { googleMap ->
            mGoogleMap = googleMap
            mGoogleMap!!.setOnMyLocationButtonClickListener { false }
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
            }
            googleMap.isMyLocationEnabled = true

            mGoogleMap!!.setOnCameraIdleListener {
                val latLng = mGoogleMap!!.cameraPosition.target
                binding.address.text = Utility.convertLatLngToAddress(this,latLng)
                addresss = address
                mLatLong = latLng
            }
            if (currentLatLong != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong!!, 16f))
            } else {
                val latLngg = LatLng(20.5937, 78.9629)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngg, 5f))
            }
            googleMap.setOnMapClickListener { latLng ->
                mLatLong = latLng
                googleMap.clear()
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
            }
        }
    }

    private fun getCurrentLocation() {
        if (Utility.isLocationEnabled(this)) {
            if (locationType == "SearchLoc") {
                /* mGoogleMap!!.animateCamera(
                     CameraUpdateFactory.newLatLngZoom(
                         currentLatLong!!,
                         16f
                     )
                 )*/
                binding.address.text = address
            } else {
                if (ActivityCompat.checkSelfPermission(
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
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    return
                }
                val task = client!!.lastLocation
                task.addOnFailureListener(this, OnFailureListener { e ->
                    e.printStackTrace()
                    //Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    if (e is ResolvableApiException) {
                        try {
                            e.startResolutionForResult(this, 51)
                        } catch (sendIntentException: SendIntentException) {
                            sendIntentException.printStackTrace()
                        }
                    }
                    /*if (currentLatLong != null) {
                        mGoogleMap!!.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLatLong!!,
                                16f
                            )
                        )
                    }*/
                })
                task.addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                        mLatLong = latLng
                        currentLatLong = mLatLong
                    }
                }
            }
        } else {
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
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1111) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                finish()
            }
        } else {
            finish()
        }
    }
}