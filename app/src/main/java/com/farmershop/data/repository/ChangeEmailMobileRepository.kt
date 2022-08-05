package com.farmershop.data.repository

import com.farmershop.data.model.request.ChangeEmailMobileRequest
import com.farmershop.data.network.RetrofitBuilder

class ChangeEmailMobileRepository {
    suspend fun changeEmailMobile(username:String,request:ChangeEmailMobileRequest) =
        RetrofitBuilder.apiService?.changeEmailMobile(username,request)
}