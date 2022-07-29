package com.farmershop.data.repository

import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.network.RetrofitBuilder

class ResetPasswordRepository {
    suspend fun resetPassword(password:String,confirm_password:String,username: String) =
        RetrofitBuilder.apiService?.resetPassword(password,confirm_password,username)
}