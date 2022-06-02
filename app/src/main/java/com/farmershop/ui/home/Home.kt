package com.farmershop.ui.home

import AppConfig
import com.exfactor.appsdk.AppSession
import com.farmershop.ExitActivity
import com.farmershop.R
import com.farmershop.ui.product.Cart
import com.farmershop.ui.search.Search
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.farmershop.appSDK.*
import com.farmershop.data.network.ApiPojo
import com.farmershop.ui.product.ProductViewModel
import com.farmershop.ui.profile.Location
import com.farmershop.ui.profile.Profile
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.category_items.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.include_toolbar_home.*
import kotlinx.android.synthetic.main.include_toolbar_home.cartLayout
import kotlinx.android.synthetic.main.include_toolbar_home.cart_counter
import kotlinx.android.synthetic.main.include_toolbar_home.editSearch
import kotlinx.android.synthetic.main.include_toolbar_home.toolbar_title
import kotlinx.android.synthetic.main.include_toolbar_product.*
import kotlinx.android.synthetic.main.nav_header_layout.*
import java.lang.Exception

class Home : BaseActivityUser(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var viewModel: ProductViewModel
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
        viewModel?.baseInterface = this
        viewModel.getHomeCounter()
       try{
           setNavigationDrawer()
           bottom_navigation.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener)
           addFragment(FrgHome.newInstance())
           toolbar_title.text = AppConfig.appName
           StaticMethods.readyOnlyEditText(editSearch)
           editSearch.setOnClickListener {
               startActivity(Intent(applicationContext, Search::class.java))
           }
           cartLayout.setOnClickListener {
               startActivity(Intent(applicationContext, Cart::class.java))
           }

       }catch (ex:Exception){

       }
    }


    private fun setNavigationDrawer(){
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        btnHambarger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener(this)

        //----------Navigation Header-------------
        val headerView=navigationView.getHeaderView(0)
        var header_name=headerView.findViewById<TextView>(R.id.header_name)
        var header_location=headerView.findViewById<TextView>(R.id.header_location)
        var header_location_icon=headerView.findViewById<ImageView>(R.id.header_location_icon)

        if(Validation.isEmpty(AppSession.getInstance(applicationContext).getName().toString())){
            header_name.text=StaticMethods.getString("Hello, Guest")
        }else{
            header_name.text=StaticMethods.getString("Hello, "+ AppSession.getInstance(applicationContext).getName().toString())
        }


        var location=AppSession.getInstance(applicationContext).getLocation().toString()
        if(Validation.isEmpty(location)){
            header_location.text="Your Location"
        }else{
            header_location.text=location
        }

        header_name.setOnClickListener {
            startActivity(Intent(applicationContext,Profile::class.java))
        }
        header_location.setOnClickListener {
            startActivity(Intent(applicationContext,Location::class.java))
        }
        header_location_icon.setOnClickListener {
            startActivity(Intent(applicationContext,Location::class.java))
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.nav_home -> {
                addFragment(FrgHome.newInstance())
                true
            }
            R.id.nav_logout -> {
                AppSession.getInstance(applicationContext).logout()
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
                    addFragment(FrgHome.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_category -> {
                    addFragment(FrgHome.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_search -> {
                    addFragment(FrgHome.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_offers -> {
                    addFragment(FrgHome.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_account -> {
                    addFragment(FrgHome.newInstance())
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
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                backToClose()
            }
        }
    }

    //-----------API Response------------
    override fun onApiStart() {
        progressBar?.show()
    }
    override fun onApiSuccess(apiResponse: LiveData<ApiPojo>, responseFlag: String?) {
        apiResponse.observe(this, Observer {
            progressBar?.hide()
            val response = apiResponse.value!!
            Log.e(TAG, response.toString())
            val message = response.message.toString()
            if (response.status.toString() == "1") {
                if (responseFlag == "getHomeCounter") {
                    val data = response.data
                    if(data!!.item_count!=null && data!!.item_count.toString().toInt()>0){
                        cartLayout.visibility= View.VISIBLE
                        cart_counter.text=data!!.item_count.toString()
                    }else{
                        cartLayout.visibility= View.GONE
                    }
                }
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onApiFailed(message: String) {
        progressBar?.hide()
        Log.e("onApiFailed", message)
        toast(message)
    }

}