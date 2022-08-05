package com.farmershop.ui.activity

import com.farmershop.utils.AppConfig
import android.Manifest
import com.farmershop.R
import android.provider.Settings
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.farmershop.ui.fragment.CategoryFragment
import com.farmershop.ui.fragment.HomeFragment
import com.farmershop.ui.fragment.MyAccountFragment
import com.farmershop.ui.fragment.ProductFragment
import com.farmershop.data.viewModel.ProductViewModel
import com.farmershop.ui.activity.auth.Login
import com.farmershop.utils.*
import com.farmershop.ui.base.BaseActivityUser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.include_toolbar_home.*
import kotlinx.android.synthetic.main.include_toolbar_home.toolbar_title

class Home : BaseActivityUser(), NavigationView.OnNavigationItemSelectedListener {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    var isLocationOn = false
    lateinit var viewModel: ProductViewModel
    lateinit var header_location:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setNavigationDrawer()
        bottom_navigation.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener)
        toolbar_title.text = AppConfig.appName
        txt_searchProduct.setOnClickListener {
            intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        tvLocation.setOnClickListener {
            startActivity(Intent(applicationContext, SearchLocationActivity::class.java))
            drawerLayout.closeDrawers()
        }
        addFragment(HomeFragment.newInstance())

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
        if (isLocationOn) {
            getLastLocation()
            isLocationOn = false
        }
    }

    private fun setNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        btnHambarger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener(this)

        //----------Navigation Header-------------
        val headerView = navigationView.getHeaderView(0)
        var header_name = headerView.findViewById<TextView>(R.id.header_name)
        header_location = headerView.findViewById<TextView>(R.id.header_location)
        var header_location_icon = headerView.findViewById<ImageView>(R.id.header_location_icon)

        if (Validation.isEmpty(AppSession.getName().toString())) {
            header_name.text = StaticMethods.getString("Hello, Guest")
        } else {
            header_name.text = StaticMethods.getString(
                "Hello, " + AppSession.getName().toString()
            )
        }


       /* val location = AppSession.getInstance(this).getCurrAddress().toString()
        if (Validation.isEmpty(location)) {
            header_location.text = "Your Location"

        } else {
            header_location.text = location

        }*/

        header_name.setOnClickListener {
            startActivity(Intent(applicationContext, Profile::class.java))
            drawerLayout.closeDrawers()
        }
        header_location.setOnClickListener {
            startActivity(Intent(applicationContext, SearchLocationActivity::class.java))
            drawerLayout.closeDrawers()
        }
        header_location_icon.setOnClickListener {
            startActivity(Intent(applicationContext, SearchLocationActivity::class.java))
            drawerLayout.closeDrawers()
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.nav_home -> {
                addFragment(HomeFragment.newInstance())
                drawerLayout.closeDrawers()
                true
            }
            R.id.nav_category -> {
                addFragment(CategoryFragment.newInstance())
                drawerLayout.closeDrawers()
                true
            }
            R.id.nav_account -> {
                addFragment(MyAccountFragment.newInstance())
                drawerLayout.closeDrawers()
                true
            }
            R.id.nav_logout -> {
                AppSession.logout()
                drawerLayout.closeDrawers()
                val intent=Intent(this, Login::class.java)
                //intent.putExtra("GoogleLogout","Yes")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                true
            }
            R.id.nav_myorders -> {
                intent = Intent(this, MyOrderActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawers()
                true
            }
            R.id.nav_faq -> {
                intent = Intent(this, FAQActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }


    private val bottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Log.d("dddwqsws","Home")
                    addFragment(HomeFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_category -> {
                    addFragment(CategoryFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                /*R.id.nav_search -> {
                    addFragment(HomeFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }*/
                R.id.nav_offers -> {
                    addFragment(ProductFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_account -> {
                    addFragment(MyAccountFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun backToClose() {
        AlertDialog.Builder(this)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle("Confirmation?")
            .setMessage("Do you want to exit form this App?")
            .setPositiveButton(
                "Yes"
            ) { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
                ExitActivity.exitApp(this)
            }
            .setNegativeButton(
                "No"
            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            .create()
            .show()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                backToClose()
            }
        }
    }

    private fun getLastLocation() {
           if (!Validation.isEmpty(AppSession.getCurrAddress().toString())) {
               val location = AppSession.getCurrAddress().toString()
               if (Validation.isEmpty(location)) {
                   tvLocation.text = "Your Location"
                   header_location.text = "Your Location"

               } else {
                   tvLocation.text = location
                   header_location.text = location

               }
           } else {
               if (Utility.isLocationEnabled(this)) {
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
               }
               fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
                   if (task.isSuccessful && task.result != null) {
                       lastLocation = task.result

                       AppSession.setCurrLat(lastLocation?.latitude.toString())
                       AppSession.setCurrLong(lastLocation?.longitude.toString())
                       AppSession.setCurrAddress(
                           Utility.convertLatLngToAddress(
                               this,
                               LatLng(lastLocation?.latitude!!, lastLocation?.longitude!!)
                           )
                       )
                       val location = AppSession.getCurrAddress().toString()
                       if (Validation.isEmpty(location)) {
                           tvLocation.text = "Your Location"
                           header_location.text = "Your Location"
                       } else {
                           tvLocation.text = location
                           header_location.text = location
                       }
                       Log.d("ssassasasaa", lastLocation?.latitude.toString())
                       Log.d("ssassasasaa", lastLocation?.longitude.toString())
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