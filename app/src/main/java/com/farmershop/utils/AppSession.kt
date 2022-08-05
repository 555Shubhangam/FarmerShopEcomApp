package com.farmershop.utils

import android.app.Activity
import com.farmershop.ui.activity.auth.Login
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

object AppSession {

    fun init(context: Context) {
        if (!(::sharedPreferences.isInitialized)) {
            sharedPreferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }
    }
    private val USER_ID = "user_id"
    private val USER_NAME = "USER_NAME"
    private val GENDER = "GENDER"
    private val INTEREST_ID="INTEREST_ID"
    private val SIGNUP_COMPLETED="SIGNUP_COMPLETED"
    private val UPLOAD_PATH="UPLOAD_PATH"
    private val LOCATION="LOCATION"
    private val CURRENT_LAT="CURRENT_LAT"
    private val CURRENT_LONGI="CURRENT_LONGI"
    private val CURRENT_ADDRESS="CURRENT_ADDRESS"
    private val NAME="NAME"
    private val AADHAAR_NO="AADHAAR_NO"
    private val NICK_NAME="NICK_NAME"
    private val FRAGMENT_NAME="FRAGMENT_NAME"
    private val KEY_PHOTO = "KEY_PHOTO"
    private val TOKEN = "TOKEN"
    private val screenName="screenName"
    private val mobile="mobile"
    private val ADDRESS="ADDRESS"
    private val DOB="ADDRESS"
    private val ZIP="ZIP"
    private val STATE="STATE"
    private val STATE_ID="STATE_ID"
    private val CITY="CITY"
    private val email="email"
    private lateinit var sharedPreferences: SharedPreferences

    fun setEmail(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(email,data)
        editor?.apply()
        editor?.commit()
    }

    fun getEmail():String?{
        return sharedPreferences?.getString(email, null)
    }

    fun setMobile(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(mobile,data)
        editor?.apply()
        editor?.commit()
    }

    fun getMobile():String?{
        return sharedPreferences?.getString(mobile, null)
    }

    fun setToken(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(TOKEN,data)
        editor?.apply()
        editor?.commit()
    }
    fun getToken():String?{
        return sharedPreferences?.getString(TOKEN, null)
    }

    fun setUserId(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(USER_ID,data)
        editor?.apply()
        editor?.commit()
    }

    fun getUserId():String?{
        return sharedPreferences?.getString(USER_ID, null)
    }

    fun setUserName(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(USER_NAME,data)
        editor?.apply()
        editor?.commit()
    }

    fun getUserName():String?{
        return sharedPreferences?.getString(USER_NAME, null)
    }

    fun setGender(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(GENDER,data)
        editor?.apply()
        editor?.commit()
    }

    fun getGender():String?{
        return sharedPreferences?.getString(GENDER, null)
    }

    fun isLoggedIn():Boolean{
        return getUserId()!=null
    }

    fun setIsSignUpCompleted(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(SIGNUP_COMPLETED,data)
        editor?.apply()
        editor?.commit()
    }
    fun isSignUpCompleted():Boolean{
        val is_signup_complated= sharedPreferences?.getString(SIGNUP_COMPLETED, null)
        return is_signup_complated!=null && is_signup_complated=="1"
    }

    fun setPhoto(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(KEY_PHOTO,data)
        editor?.apply()
        editor?.commit()
    }
    fun getPhoto():String?{
        return sharedPreferences?.getString(KEY_PHOTO, null)
    }
    fun setLocation(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(LOCATION,data)
        editor?.apply()
        editor?.commit()
    }
    fun getLocation():String?{
        return sharedPreferences?.getString(LOCATION, null)
    }


    fun setName(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(NAME,data)
        editor?.apply()
        editor?.commit()
    }
    fun getName():String?{
        return sharedPreferences?.getString(NAME, null)
    }

    fun setAadhaarNo(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(AADHAAR_NO,data)
        editor?.apply()
        editor?.commit()
    }
    fun getAadhaarNo():String?{
        return sharedPreferences?.getString(AADHAAR_NO, null)
    }

    fun setDob(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(DOB,data)
        editor?.apply()
        editor?.commit()
    }
    fun getDob():String?{
        return sharedPreferences?.getString(DOB, null)
    }

    fun setZip(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(ZIP,data)
        editor?.apply()
        editor?.commit()
    }
    fun getZip():String?{
        return sharedPreferences?.getString(ZIP, null)
    }

    fun setState(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(STATE,data)
        editor?.apply()
        editor?.commit()
    }
    fun getState():String?{
        return sharedPreferences?.getString(STATE, null)
    }

    fun setStateID(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(STATE_ID,data)
        editor?.apply()
        editor?.commit()
    }
    fun getStateID():String?{
        return sharedPreferences?.getString(STATE_ID, null)
    }

    fun setCity(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CITY,data)
        editor?.apply()
        editor?.commit()
    }
    fun getCity():String?{
        return sharedPreferences?.getString(CITY, null)
    }

    fun setCurrLat(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CURRENT_LAT,data)
        editor?.apply()
        editor?.commit()
    }
    fun getCurrLat():String?{
        return sharedPreferences?.getString(CURRENT_LAT, null)
    }

    fun setCurrLong(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CURRENT_LONGI,data)
        editor?.apply()
        editor?.commit()
    }
    fun getCurrLong():String?{
        return sharedPreferences?.getString(CURRENT_LONGI, null)
    }

    fun setCurrAddress(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CURRENT_ADDRESS,data)
        editor?.apply()
        editor?.commit()
    }
    fun getCurrAddress():String?{
        return sharedPreferences?.getString(CURRENT_ADDRESS, null)
    }

    fun setAddress(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(ADDRESS,data)
        editor?.apply()
        editor?.commit()
    }
    fun getAddress():String?{
        return sharedPreferences?.getString(ADDRESS, null)
    }

    //calling this method will clear FCM key
    fun clearAllPref() {
        sharedPreferences.edit()?.clear()?.apply()
    }
    fun logout() {
        clearAllPref()
    }
    fun read(key: String, defValue: String): String {
        return sharedPreferences.getString(key, defValue)!!
    }

    fun read(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun read(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    fun read(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun write(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun write(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun write(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun write(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

}
/*
open class AppSession private constructor(context: Context) {
    init {
        ctx = context
        sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    companion object {
        private val SHARED_PREF_NAME = "linkcxoSharedPreferences"
        private val USER_ID = "user_id"
        private val USER_NAME = "USER_NAME"
        private val GENDER = "GENDER"
        private val INTEREST_ID="INTEREST_ID"
        private val SIGNUP_COMPLETED="SIGNUP_COMPLETED"
        private val UPLOAD_PATH="UPLOAD_PATH"
        private val LOCATION="LOCATION"
        private val CURRENT_LAT="CURRENT_LAT"
        private val CURRENT_LONGI="CURRENT_LONGI"
        private val CURRENT_ADDRESS="CURRENT_ADDRESS"
        private val NAME="NAME"
        private val AADHAAR_NO="AADHAAR_NO"
        private val NICK_NAME="NICK_NAME"
        private val FRAGMENT_NAME="FRAGMENT_NAME"
        private val KEY_PHOTO = "KEY_PHOTO"
        private val TOKEN = "TOKEN"
        private val screenName="screenName"
        private val mobile="mobile"
        private val ADDRESS="ADDRESS"
        private val DOB="ADDRESS"
        private val ZIP="ZIP"
        private val STATE="STATE"
        private val STATE_ID="STATE_ID"
        private val CITY="CITY"
        private val email="email"
        private var mInstance: AppSession? = null
        private var ctx: Context? = null
        private var sharedPreferences: SharedPreferences?=null
        @Synchronized
        fun getInstance(context: Context): AppSession {
            if (mInstance == null) {
                mInstance = AppSession(context)
            }
            return mInstance as AppSession
        }
    }


    open fun setEmail(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(email,data)
        editor?.apply()
        editor?.commit()
    }

    open fun getEmail():String?{
        return sharedPreferences?.getString(email, null)
    }

    open fun setMobile(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(mobile,data)
        editor?.apply()
        editor?.commit()
    }

    open fun getMobile():String?{
        return sharedPreferences?.getString(mobile, null)
    }

    open fun setToken(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(TOKEN,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getToken():String?{
        return sharedPreferences?.getString(TOKEN, null)
    }

    fun setScreenData(data:String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(screenName, data)
        editor?.apply()
    }

    fun getScreenData():String? {
        return sharedPreferences?.getString(screenName, null)
    }

    open fun setActiveFragment(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(FRAGMENT_NAME,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getActiveFragment():String?{
        return sharedPreferences?.getString(FRAGMENT_NAME, null)
    }

    open fun setUserId(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(USER_ID,data)
        editor?.apply()
        editor?.commit()
    }

    open fun getUserId():String?{
        return sharedPreferences?.getString(USER_ID, null)
    }

    open fun setUserName(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(USER_NAME,data)
        editor?.apply()
        editor?.commit()
    }

    open fun getUserName():String?{
        return sharedPreferences?.getString(USER_NAME, null)
    }

    open fun setGender(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(GENDER,data)
        editor?.apply()
        editor?.commit()
    }

    open fun getGender():String?{
        return sharedPreferences?.getString(GENDER, null)
    }

    open fun isLoggedIn():Boolean{
        return getUserId()!=null
    }

    open fun setIsSignUpCompleted(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(SIGNUP_COMPLETED,data)
        editor?.apply()
        editor?.commit()
    }
    open fun isSignUpCompleted():Boolean{
        val is_signup_complated= sharedPreferences?.getString(SIGNUP_COMPLETED, null)
        return is_signup_complated!=null && is_signup_complated=="1"
    }

    open fun setPhoto(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(KEY_PHOTO,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getPhoto():String?{
        return sharedPreferences?.getString(KEY_PHOTO, null)
    }

    open fun setUploadImagePath(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(UPLOAD_PATH,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getUploadImagePath():String?{
        return sharedPreferences?.getString(UPLOAD_PATH, null)
    }

    open fun clearUploadImagePath(){
        val editor=  sharedPreferences?.edit()
        editor?.remove(UPLOAD_PATH)
        editor?.commit();
    }

    open fun setLocation(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(LOCATION,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getLocation():String?{
        return sharedPreferences?.getString(LOCATION, null)
    }


    open fun setName(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(NAME,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getName():String?{
        return sharedPreferences?.getString(NAME, null)
    }

    open fun setAadhaarNo(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(AADHAAR_NO,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getAadhaarNo():String?{
        return sharedPreferences?.getString(AADHAAR_NO, null)
    }

    open fun setDob(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(DOB,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getDob():String?{
        return sharedPreferences?.getString(DOB, null)
    }

    open fun setZip(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(ZIP,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getZip():String?{
        return sharedPreferences?.getString(ZIP, null)
    }

    open fun setState(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(STATE,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getState():String?{
        return sharedPreferences?.getString(STATE, null)
    }

    open fun setStateID(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(STATE_ID,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getStateID():String?{
        return sharedPreferences?.getString(STATE_ID, null)
    }

    open fun setCity(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CITY,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getCity():String?{
        return sharedPreferences?.getString(CITY, null)
    }

    open fun setCurrLat(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CURRENT_LAT,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getCurrLat():String?{
        return sharedPreferences?.getString(CURRENT_LAT, null)
    }

    open fun setCurrLong(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CURRENT_LONGI,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getCurrLong():String?{
        return sharedPreferences?.getString(CURRENT_LONGI, null)
    }

    open fun setCurrAddress(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(CURRENT_ADDRESS,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getCurrAddress():String?{
        return sharedPreferences?.getString(CURRENT_ADDRESS, null)
    }

    open fun setAddress(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(ADDRESS,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getAddress():String?{
        return sharedPreferences?.getString(ADDRESS, null)
    }

    open fun setInterestId(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(INTEREST_ID,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getInterestId():String?{
        return sharedPreferences?.getString(INTEREST_ID, null)
    }

    open fun clearNames(){
        val editor=  sharedPreferences?.edit()
        editor?.remove(NAME)
        editor?.remove(NICK_NAME)
        editor?.commit();
    }

    open fun setNickName(data:String){
        val editor=  sharedPreferences?.edit()
        editor?.putString(NICK_NAME,data)
        editor?.apply()
        editor?.commit()
    }
    open fun getNickName():String?{
        return sharedPreferences?.getString(NICK_NAME, null)
    }
    fun clearAllPref() {
        sharedPreferences?.edit()?.clear()?.apply()
    }
    private fun clearSession(){
        val auth= FirebaseAuth.getInstance()
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        auth.signOut()
    }

    //this method will logout the user
    fun logout() {
        clearAllPref()
        clearSession()
        val intent=Intent(ctx, Login::class.java)
        //intent.putExtra("GoogleLogout","Yes")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ctx?.startActivity(intent)

    }

}*/
