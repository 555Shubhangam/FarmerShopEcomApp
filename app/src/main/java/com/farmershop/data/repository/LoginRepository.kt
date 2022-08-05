package com.farmershop.data.repository

import com.farmershop.data.network.RetrofitBuilder

class LoginRepository {

    suspend fun signIn(username: String,password:String) =
        RetrofitBuilder.apiService?.signIn(username,password)

   /* suspend fun forgetPassword(request: ForgetPasswordRequest) =
        RetrofitBuilder.apiService?.forgetPassword(request)*/
}