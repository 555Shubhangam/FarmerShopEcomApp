package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ForgotPasswordRequest
import com.farmershop.data.model.request.OTPRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.ForgotPasswordRepository
import com.farmershop.data.repository.OTPRepository
import com.farmershop.data.repository.ResetPasswordRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class OTPViewModal : ViewModel(){

    private val repository = OTPRepository()
    val otpResponse = MutableLiveData<Resource<String>>()

    fun otpVerification(username: String,otp:String) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            otpResponse.postValue(Resource.Loading())
            val response = repository.otp(username,otp)
            otpResponse.postValue(handleOTPResponse(response))
        } else {
            otpResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleOTPResponse(response: Response<OTPResponse>?): Resource<String> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status){
                    Resource.Success(res.message, res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        //return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
        return Resource.Error(response.message())
    }
}