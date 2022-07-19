package com.farmershop.data.repository

import com.farmershop.data.model.request.ForgetPasswordRequest
import com.farmershop.data.model.request.LoginRequest
import com.farmershop.data.network.RetrofitBuilder


/**
 * Created by Amit Gupta on 16-05-2021.
 */
class LoginRepository {

    suspend fun signIn(username: String,password:String) =
        RetrofitBuilder.apiService?.signIn(username,password)

    suspend fun forgetPassword(request: ForgetPasswordRequest) =
        RetrofitBuilder.apiService?.forgetPassword(request)

  /*  suspend fun resetPassword(request: ResetPasswordRequest) =
        RetrofitInstance.apiService?.resetPassword(request)

    suspend fun verifyOtp(request: VerifyOtpRequest) =
        RetrofitInstance.apiService?.verifyOtp(request)

    suspend fun logout() =
        RetrofitInstance.apiService?.logout()*/
}