package com.farmershop.appSDK


import AppConfig
import com.farmershop.R
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmershop.data.network.DataBin
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object StaticMethods {

    open fun alert(activity: Activity,msg: String) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setIcon(R.mipmap.ic_launcher)
        dialogBuilder.setTitle(AppConfig.appName)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(AppConfig.appName)
        if(!activity.isFinishing){
            alert.show()
        }
    }

    fun setUserSession(mContext: Context,data:DataBin){
        AppSession.getInstance(mContext).setUserId(data.customer_id.toString())
        AppSession.getInstance(mContext).setMobile(data.mobile.toString())
        AppSession.getInstance(mContext).setEmail(data.email.toString())
        AppSession.getInstance(mContext).setName(data.name.toString())
        AppSession.getInstance(mContext).setPhoto(data.photo.toString())
        AppSession.getInstance(mContext).setLocation(data.location.toString())
    }
    fun getString(data: String): String {
        var data1 = ""
        if (data != "null" && data != "NULL") {
            data1 =data
        }
        return data1
    }

   fun setStrikeThrough(view:TextView){
       view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
   }

    open fun readyOnlyEditText(edt: EditText) {
        edt.isClickable = true
        edt.isFocusable = false
        edt.isCursorVisible = false
        edt.keyListener = null
        edt.showSoftInputOnFocus = false
    }

    fun log(tag:String,msg:String){
        android.util.Log.e(tag,msg);
    }

    fun getCurrentDatetime(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        //val now = LocalDateTime.now()
        val datetime = Calendar.getInstance()
        return inputFormat.format(datetime.time).toString()
    }

    //get the current version number and name

    fun openUrl(context: Context, url: String?) {
        try {
            val webpage: Uri = Uri.parse(url)
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(myIntent)
        } catch (ex: ActivityNotFoundException) {
            Log.e("openUrl", ex.toString())
        }
    }

    fun formatTimeAgo(date1: String): String {  // Note : date1 must be in   "yyyy-MM-dd hh:mm:ss"   format
        var conversionTime = ""
        try {
            val format = "yyyy-MM-dd hh:mm:ss"

            val sdf = SimpleDateFormat(format)

            val datetime = Calendar.getInstance()
            var date2 = sdf.format(datetime.time).toString()

            val dateObj1 = sdf.parse(date1)
            val dateObj2 = sdf.parse(date2)
            val diff = dateObj2.time - dateObj1.time

            val diffDays = diff / (24 * 60 * 60 * 1000)
            val diffhours = diff / (60 * 60 * 1000)
            val diffmin = diff / (60 * 1000)
            val diffsec = diff / 1000
            if (diffDays > 1) {
                conversionTime += diffDays.toString() + " days "
            } else if (diffhours > 1) {
                conversionTime += (diffhours - diffDays * 24).toString() + " hours "
            } else if (diffmin > 1) {
                conversionTime += (diffmin - diffhours * 60).toString() + " min "
            } else if (diffsec > 1) {
                conversionTime += (diffsec - diffmin * 60).toString() + " sec "
            }
        } catch (ex: java.lang.Exception) {
            Log.e("formatTimeAgo", ex.toString())
        }
        if (conversionTime != "") {
            conversionTime += "ago"
        }
        if (conversionTime == "") {
            conversionTime = "Just Now"
        }
        return conversionTime
    }




    open fun showAlert(activity: Activity,msg:String) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setIcon(R.mipmap.ic_launcher)
        alert.setTitle(AppConfig.appName)
        if(!activity.isFinishing){
            alert.show()
        }
    }

    fun showHideList(
        status: Int,
        recyclerView: RecyclerView,
        emptyView: RelativeLayout,
        emptyText: TextView,
        emptyString: String?
    ) {
        if (status === 1) {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            if (emptyString != null) {
                emptyText.text = emptyString
            }
        }
    }

    fun getNamedPhoto(data: String): String {
        var response = ""
        try {
            response = data.substring(0, 1).toUpperCase()
        } catch (ex: Exception) {
        }
        return response
    }

    fun convertToInt(data: String): Int {
        var response = 0
        try {
            response = Integer.parseInt(data)
        } catch (ex: Exception) {
            response = 0
        }
        return response
    }

    fun jsonToCsv(jsonString: String): String {
        val jsonArray1 = JSONArray(jsonString)
        var response = ""
        if (jsonArray1.length() > 0) {
            for (i in 0 until jsonArray1.length()) {
                response += jsonArray1.getJSONObject(i).toString() + ","
            }
        }
        return response
    }

    fun getSubString(data: String, length: Int): String {
        return if (data.length > length) {
            data.trim().substring(0, length) + "..."
        } else {
            data.trim()
        }
    }

    fun loadImage(mContext: Context, url: String, img: ImageView) {
        Glide
            .with(mContext)
            .load(url)
            .centerCrop()
            .into(img);
    }

    fun loadImageWithPlaceholder(mContext: Context, url: String, img1: ImageView) {
        Glide
            .with(mContext)
            .load(url)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(img1);
    }

    fun stringArrayListToJson(stringArrayList: ArrayList<String>): JsonArray {
        val gson = GsonBuilder().create()
        return gson.toJsonTree(stringArrayList).asJsonArray
    }

    fun jsonArrayToStringArrayList(strJsonArray: String): ArrayList<String> {
        val list = ArrayList<String>()
        try {
            val array = JSONArray(strJsonArray)
            for (i in 0 until array.length()) {
                list.add(array.get(i).toString())
            }
        } catch (ex: java.lang.Exception) {

        }
        return list
    }

}