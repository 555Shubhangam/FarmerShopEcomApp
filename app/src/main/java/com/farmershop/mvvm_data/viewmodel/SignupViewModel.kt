/*
package com.farmershop.mvvm_data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response

class SignupViewModel : ViewModel() {
    val singupResponseModel = MutableLiveData<Response<SignupResponseModel>>()

    fun getSignup(authToken:String,requestModel: SignupRequestModel){
        viewModelScope.launch {
            val response = RetrofitBuilder.getRetrofit(authToken).signupApi(requestModel)
            singupResponseModel.value = response
        }
    }
}*/
