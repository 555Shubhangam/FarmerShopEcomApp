package com.farmershop.mvvm_data.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    /*@POST("login")
   suspend fun loginApi(@Body requestModel: LoginRequestModel): Call<LoginResponseModel>

    @POST("signup")
    suspend fun signupApi(@Body requestModel: SignupRequestModel): Response<SignupResponseModel>*/

 /*   @POST("verify-otp")
    fun verifyOtp(@Body requestModel: VerifyOtpRequestModel): Call<VerifyOtpResponseModel>

    @POST("resend-otp")
    fun resendOtp(@Body requestModel: ResendOtpRequestModel): Call<ResendOtpResponseModel>

    *//*@POST("forget-password")
    fun forgotPasswordOtp(@Body requestModel: ForgotRequestModel): Call<ForgotResponseModel>*//*

    @POST("reset-password")
    fun resetPassword(
        @Header("Authorization") token: String,
        @Body requestModel: ResetPasswordRequestModel
    ): Call<ResetPasswordResponseModel>

    @GET("notification-list")
    fun getNotificationList(@Header("Authorization") token: String): Call<NotificationResponseModel>*/
}