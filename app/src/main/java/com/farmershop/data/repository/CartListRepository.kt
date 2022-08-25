package com.farmershop.data.repository

import com.farmershop.data.network.RetrofitBuilder

class CartListRepository {
    suspend fun getCartList(customerId: Int) =
        RetrofitBuilder.apiService?.cartList(customerId)

}