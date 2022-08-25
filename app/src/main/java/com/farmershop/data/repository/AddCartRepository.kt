package com.farmershop.data.repository

import com.farmershop.data.model.request.*
import com.farmershop.data.network.RetrofitBuilder

class AddCartRepository {
    suspend fun addCart(request: AddCartRequest) =
        RetrofitBuilder.apiService?.addCart(request)
}