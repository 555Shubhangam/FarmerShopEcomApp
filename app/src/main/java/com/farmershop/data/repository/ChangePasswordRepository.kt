package com.farmershop.data.repository

import com.farmershop.data.model.request.ChangePasswordRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.network.RetrofitBuilder

class ChangePasswordRepository {

    suspend fun changePassword(request: ChangePasswordRequest) =
        RetrofitBuilder.apiService?.changePassword(request)
}