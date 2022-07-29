package com.farmershop.data.repository

import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.network.RetrofitBuilder

class ProfileViewRepository {
    suspend fun viewProfile() =
        RetrofitBuilder.apiService?.profileView()
}