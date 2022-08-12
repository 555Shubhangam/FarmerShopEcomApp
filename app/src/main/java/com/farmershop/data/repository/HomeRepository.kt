package com.farmershop.data.repository

import com.farmershop.data.network.RetrofitBuilder

class HomeRepository {
    suspend fun getBanner() =
        RetrofitBuilder.apiService?.getBanner()
}