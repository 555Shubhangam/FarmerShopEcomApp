package com.farmershop.data.network

import com.farmershop.data.model.request.ForgetPasswordRequest
import com.farmershop.data.model.request.LoginRequest
import com.farmershop.data.model.request.RegisterRequest
import com.farmershop.data.model.response.ForgetPasswordResponse
import com.farmershop.data.model.response.LoginResponse
import com.farmershop.data.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
       @GET("auth/login")
    suspend fun signIn(@Query("username") username:String,
                       @Query("password") password:String): Response<LoginResponse>
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("forget_password")
    suspend fun forgetPassword(@Body request: ForgetPasswordRequest): Response<ForgetPasswordResponse>
}