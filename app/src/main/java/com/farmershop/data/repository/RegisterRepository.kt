package com.farmershop.data.repository

import com.farmershop.data.model.request.RegisterRequest
import com.farmershop.data.network.RetrofitBuilder

class RegisterRepository {

    suspend fun register(request: RegisterRequest) =
        RetrofitBuilder.apiService?.register(request)
}