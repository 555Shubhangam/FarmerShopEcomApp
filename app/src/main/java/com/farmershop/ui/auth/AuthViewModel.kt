package com.farmershop.ui.auth

import com.farmershop.appSDK.Validation
import com.farmershop.data.`interface`.BaseInterface
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.repository.AuthRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AuthViewModel (application: Application) : AndroidViewModel(application) {
    lateinit var baseInterface: BaseInterface

    fun login(username:String,password:String){
        if(Validation.isValidMobile(username)){
            baseInterface.onApiStart()
            val response=AuthRepository().login(username,password)
            baseInterface.onApiSuccess(response,"Login")
        }else{
            baseInterface.onApiFailed("Please enter valid mobile Number")
        }
    }

    fun otpLogin(customer_id:String){
        baseInterface.onApiStart()
        val response=AuthRepository().otpLogin(customer_id)
        baseInterface.onApiSuccess(response,"OTPLogin")
    }

    fun checkUsername(username:String){
        if(Validation.isValidMobile(username)){
            baseInterface.onApiStart()
            val response=AuthRepository().checkUsername(username)
            baseInterface.onApiSuccess(response,"checkUsername")
        }else{
            baseInterface.onApiFailed("Please enter valid mobile Number/Email Id")
        }
    }

    fun registerUser(mobile:String,email:String,name:String,password:String){
        baseInterface.onApiStart()
        val response=AuthRepository().registerUser(mobile,email,name,password)
        baseInterface.onApiSuccess(response,"Register")
    }


}