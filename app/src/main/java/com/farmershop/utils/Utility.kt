package com.farmershop.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.farmershop.R
import com.farmershop.data.adapter.PopupDropDownListAdapter
import com.farmershop.data.model.PopupDropDownListModel
import com.farmershop.databinding.CustomDropDownItemLayoutBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Utility {
    private var popupArrayList: ArrayList<PopupDropDownListModel> = ArrayList()
    private lateinit var itemDropdownBinding: CustomDropDownItemLayoutBinding
    lateinit var onClick: DatePickerInterface
    private lateinit var mPopupAdapter: PopupDropDownListAdapter
    lateinit var myCalendar: Calendar

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
    fun getRealPathFromURI(context: Activity,contentUri: Uri?): String? {
        var path: String? = null
        val proj = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)!!
        if (cursor.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            path = cursor.getString(column_index)
        }
        cursor.close()
        return path
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
    fun nullCheck(value: String): String {
        var finalStr = ""
        if (value == "null" || value == null || value == "") {
            finalStr == ""
        } else {
            finalStr = value
        }
        return finalStr
    }



    fun showPopUpWindow(
        context: Context,
        arrayList: ArrayList<PopupDropDownListModel>,
        dropDownListInterface: DropDownListInterface,
        isSearchVisible:Boolean
    ) {
        popupArrayList = arrayList
        // inflate the layout of the popup window
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_drop_down_item_layout, null, false)
        (context as Activity).window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setContentView(view)
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        // window.setBackgroundDrawableResource(R.color.colorTransparent)
        window.setGravity(Gravity.CENTER)


        itemDropdownBinding = DataBindingUtil.bind(view)!!

        setupRecyclerPopupView(dropDownListInterface, dialog, context)
        dialog.show()
        if(isSearchVisible){
            itemDropdownBinding.clsSearch.visibility = View.VISIBLE
        }else{
            itemDropdownBinding.clsSearch.visibility = View.GONE
        }
        itemDropdownBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                // filter your list from your input
                filter(s.toString())
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        })
    }
    fun filter(text: String?) {
        val temp: ArrayList<PopupDropDownListModel> = ArrayList()
        for (d in popupArrayList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.name.toLowerCase(Locale.ROOT).contains(text!!.toLowerCase(Locale.ROOT))) {
                temp.add(d)
            }
        }
        //update recyclerview
        mPopupAdapter.setDataList(temp)
    }

    private fun setupRecyclerPopupView(
        dropDownListInterface: DropDownListInterface,
        dialog: Dialog,
        context: Activity) {
        itemDropdownBinding.rvItems.apply {
            mPopupAdapter =
                PopupDropDownListAdapter(
                    context, object : PopupDropDownListAdapter.onClickListener {
                        override fun onSelectItem(
                            position: Int,
                            arrayList: ArrayList<PopupDropDownListModel>
                        ) {
                            hideKeyboard(context)
                            dialog.dismiss()
                            dropDownListInterface.onSelectItem(position, arrayList)
                        }
                    })
            mPopupAdapter.setDataList(popupArrayList)
            adapter = mPopupAdapter
        }
        itemDropdownBinding.imgCancel.setOnClickListener {
            hideKeyboard(context)
            dialog.dismiss()
        }
    }
    var date =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

    private fun updateLabel() {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        //edittext.setText(sdf.format(myCalendar.time))
        onClick.onClick(sdf.format(myCalendar.time))
    }
    fun datePicker(context: Context, onClick: DatePickerInterface) {
        this.onClick = onClick
        myCalendar = Calendar.getInstance()
        DatePickerDialog(
            context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

interface DropDownListInterface {
    fun onSelectItem(position: Int, arrayList: ArrayList<PopupDropDownListModel>)
}
interface DatePickerInterface {
    fun onClick(value: String)
}
interface LocationDialogCLickListner{
    fun onSettingClick()
    fun onDismissClick(dialog: DialogInterface)
}