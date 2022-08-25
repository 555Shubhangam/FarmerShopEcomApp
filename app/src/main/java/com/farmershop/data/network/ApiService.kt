package com.farmershop.data.network

import com.farmershop.data.model.request.*
import com.farmershop.data.model.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


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
    suspend fun forgotPassword(@Query("username") username: String,
                               @Query("otp_for") otp_for: String): Response<ForgotPasswordResponse>

    @GET("auth/otp_verification")
    suspend fun otp(
        @Query("username") username: String,
        @Query("otp") otp: String,
        @Query("otp_for") otpFor: String
    ): Response<OTPResponse>

    @GET("auth/reset_password")
    suspend fun resetPassword(
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String,
        @Query("username") username: String
    ): Response<ResetPasswordResponse>

    @GET("customer/profile")
    suspend fun profileView(): Response<ProfileViewResponse>

    @GET("data/state")
    suspend fun getState(): Response<StateResponse>

    @PATCH("customer/profile/{username}")
    suspend fun updateProfile(
        @Path("username") username: String,
        @Body contacts: UpdateProfileRequest
    ): Response<CommonResponse>

    @Multipart
    @POST("customer/profile_photo")
    suspend fun updateProfilePhoto(
        @Part("username") username: RequestBody?,
        @Part image: MultipartBody.Part?,
    ): Response<CommonResponse>

    @PATCH("customer/account/{username}")
    suspend fun changeEmailMobile(
        @Path("username") username: String,
        @Body request: ChangeEmailMobileRequest
    ): Response<CommonResponse>

    @GET("data/banners")
    suspend fun getBanner(): Response<BannerResponse>

    @GET("product")
    suspend fun searchProduct(
        @Query("search_key") search_key: String,
        @Query("category_id") category_id: String,
        @Query("sub_category_id") sub_category_id: String,
        @Query("page_size") page_size: String,
        @Query("page_no") page_no: String,
        @Query("customer_id") customer_id: Int
    ): Response<SearchResponse>




    @POST("order/cart")
    suspend fun addCart(@Body request: AddCartRequest): Response<AddCartResponse>


    @GET("order/cart")
    suspend fun cartList(
        @Query("customer_id") customer_id: Int
    ): Response<CartListResponse>
}