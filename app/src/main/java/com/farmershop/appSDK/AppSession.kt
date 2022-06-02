package com.exfactor.appsdk



import com.farmershop.ui.auth.Login
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppSession private constructor(context: Context) {
    init {
        ctx = context
        sharedPreferences=ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    companion object {
        private val SHARED_PREF_NAME = "linkcxoSharedPreferences"
        private val USER_ID = "user_id"
        private val INTEREST_ID="INTEREST_ID"
        private val SIGNUP_COMPLETED="SIGNUP_COMPLETED"
        private val UPLOAD_PATH="UPLOAD_PATH"
        private val LOCATION="LOCATION"
        private val NAME="NAME"
        private val NICK_NAME="NICK_NAME"
        private val FRAGMENT_NAME="FRAGMENT_NAME"
        private val KEY_PHOTO = "KEY_PHOTO"
        private val TOKEN = "TOKEN"
        private val screenName="screenName"
        private val mobile="mobile"
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
        val is_signup_complated=sharedPreferences?.getString(SIGNUP_COMPLETED, null)
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

    private fun clearSession(){
        val auth=Firebase.auth
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        auth.signOut()
    }

    //this method will logout the user
    fun logout() {
        clearSession()
        val intent=Intent(ctx, Login::class.java)
        intent.putExtra("GoogleLogout","Yes")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ctx?.startActivity(intent)

    }

}