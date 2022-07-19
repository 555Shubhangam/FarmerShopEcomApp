package com.farmershop.utils



import android.util.Patterns
import androidx.core.text.isDigitsOnly

object Validation{

    fun isValidAge(age: String,limitAge:Int): Boolean {
        return !isEmpty(age) && age?.isDigitsOnly()!! && age.toInt()>=limitAge
    }

    fun isEmpty(data: String): Boolean {
        return data==null || data.isNullOrEmpty() || data.trim()=="" || data.isEmpty() || data=="null"
    }

    fun isValidMobile(data: String?): Boolean {
        if(data.isNullOrEmpty() || data.trim().length!=10){
            return false
        }else{
            return Patterns.PHONE.matcher(data).matches()
        }
    }

    fun isValidEmail(data: String?): Boolean {
        if(data.isNullOrEmpty()){
            return false
        }else {
            return Patterns.EMAIL_ADDRESS.matcher(data).matches()
        }
    }

    fun isValidEmailOrMobile(data: String?): Boolean {
        return isValidEmail(data) || isValidMobile(data)
    }
}