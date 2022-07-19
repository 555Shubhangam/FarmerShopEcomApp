package com.farmershop.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.farmershop.R
import com.farmershop.utils.GlobalConstants
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_search_location.*

class SearchLocationActivity : AppCompatActivity() {
    private lateinit var tvErrorMsg: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)
        tvErrorMsg = findViewById(R.id.tvErrorMsg)

        val apiKey = getString(R.string.GOOGLE_MAP_KEY_NEW)
        if (apiKey.isEmpty()) {
            tvErrorMsg.visibility= View.VISIBLE
            tvErrorMsg.text = getString(R.string.error)
            return
        }
        else{
            tvErrorMsg.visibility= View.GONE
        }

        // Initializing the Places API
        // with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }
        // Initialize Autocomplete Fragments
        // from the main activity layout file
        val autocompleteSupportFragment1 =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment1) as AutocompleteSupportFragment?

        // Information that we wish to fetch after typing
        // the location and clicking on one of the options
        autocompleteSupportFragment1?.setPlaceFields(
            listOf(

                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.PHONE_NUMBER,
                Place.Field.LAT_LNG,
                Place.Field.OPENING_HOURS,
                Place.Field.RATING,
                Place.Field.USER_RATINGS_TOTAL

            )
        )

        // Display the fetched information after clicking on one of the options
        autocompleteSupportFragment1?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                // Text view where we will
                // append the information that we fetch
                //val textView = findViewById<TextView>(R.id.tv1)

                // Information about the place
                val name = place.name
                val address = place.address
                val phone = place.phoneNumber.toString()
                val latlng = place.latLng
                val latitude = "" + latlng?.latitude
                val longitude = "" + latlng?.longitude
      //its very import in future i can use
                /*val intent = Intent()
                intent.putExtra("key", address)
                intent.putExtra("lat_k", latitude)
                intent.putExtra("long_k", longitude)
                setResult(111, intent)
                finish()*/
               /* sharedPreference!!.putString(
                    GlobalConstants.CURRENT_ADDRESS,
                    address
                )
                sharedPreference!!.putString(
                    GlobalConstants.CURRENT_LAT,
                    latitude
                )
                sharedPreference!!.putString(
                    GlobalConstants.CURRENT_LONGI,
                    longitude
                )*/
               intent=Intent(this@SearchLocationActivity, MapActivity::class.java)
                intent.putExtra(GlobalConstants.ADDRESS_INTENT, address)
                intent.putExtra(GlobalConstants.LAT_INTENT, latitude)
                intent.putExtra(GlobalConstants.LONG_INTENT, longitude)
                intent.putExtra(GlobalConstants.LOCATION_TYPE_INTENT, "SearchLoc")
                startActivity(intent)

                val isOpenStatus: String = if (place.isOpen == true) {
                    "Open"
                } else {
                    "Closed"
                }

                val rating = place.rating
                val userRatings = place.userRatingsTotal

                //finish()
                // textView.text = "Name: $name \nAddress: $address \nPhone Number: $phone \n" +
                //       "Latitude, Longitude: $latitude , $longitude \nIs open: $isOpenStatus \n" +
                //     "Rating: $rating \nUser ratings: $userRatings"
            }

            override fun onError(status: Status) {
                Toast.makeText(applicationContext, "Some error occurred", Toast.LENGTH_SHORT).show()
            }
        })


        CurrentLocation.setOnClickListener {
            intent= Intent(this@SearchLocationActivity, MapActivity::class.java)
            intent.putExtra(GlobalConstants.LOCATION_TYPE_INTENT, "CurrentLoc")
            startActivity(intent)
        }
    }
}


