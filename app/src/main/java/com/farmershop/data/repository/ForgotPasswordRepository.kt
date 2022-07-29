package com.farmershop.data.repository

import com.farmershop.data.model.request.ForgotPasswordRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.network.RetrofitBuilder

class ForgotPasswordRepository {
    suspend fun forgotPassword(username: String) =
        RetrofitBuilder.apiService?.forgotPassword(username)
}