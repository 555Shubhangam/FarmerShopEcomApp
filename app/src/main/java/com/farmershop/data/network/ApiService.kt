package com.farmershop.data.network

import com.farmershop.data.model.request.*
import com.farmershop.data.model.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("auth/login")
    suspend fun signIn(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("customer/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponse>

    @GET("auth/forgot_password")
    suspend fun forgotPassword(@Query("username") username: String): Response<ForgotPasswordResponse>

    @GET("auth/otp_verification")
    suspend fun otp(@Query("username") username: String,@Query("otp") otp: String): Response<OTPResponse>

    @GET("auth/reset_password")
    suspend fun resetPassword(@Query("password") password: String,@Query("password_confirmation") password_confirmation: String,@Query("username") username: String): Response<ResetPasswordResponse>

    @GET("customer/profile")
    suspend fun profileView(): Response<ProfileViewResponse>
}