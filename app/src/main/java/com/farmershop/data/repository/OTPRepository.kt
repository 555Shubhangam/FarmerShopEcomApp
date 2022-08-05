package com.farmershop.data.repository

import com.farmershop.data.model.request.ForgotPasswordRequest
import com.farmershop.data.model.request.OTPRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.network.RetrofitBuilder

class OTPRepository {
    suspend fun otp(username: String,otp:String,otpFor:String) =
        RetrofitBuilder.apiService?.otp(username,otp,otpFor)
}