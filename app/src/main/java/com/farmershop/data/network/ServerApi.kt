package com.farmershop.data.network



import com.farmershop.BuildConfig
import android.util.Log
import com.farmershop.appSDK.StaticMethods
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface ServerApi {

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("api_key") api_key: String?,
        @Field("mobile") mobile: String?,
        @Field("email") email: String?,
        @Field("name") password: String?,
        @Field("password") name: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("api_key") api_key: String?,
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("otp_login")
    fun otpLogin(
        @Field("api_key") api_key: String?,
        @Field("customer_id") customer_id: String?
    ): Call<ApiPojo>


    @FormUrlEncoded
    @POST("reset_password")
    fun resetPassword(
        @Field("username") username: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("facebook_login")
    fun facebookLogin(
        @Field("email") email: String?,
        @Field("name") name: String?,
        @Field("account_type") account_type: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("create_password")
    fun createPassword(
        @Field("user_id") user_id: String?,
        @Field("password") password: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("email_otp_verification")
    fun verifyEmailOTP(
        @Field("user_id") user_id: String?,
        @Field("otp") otp: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("send_otp")
    fun sendEmailOTP(
        @Field("username") user_id: String?
    ): Call<ApiPojo>

    @Multipart
    @POST("manage_profile_photo")
    fun manageProfilePhoto(
        @Part("user_id") user_id: RequestBody?,
        @Part("image\"; filename=\"pp.png\" ") image: RequestBody?
    ): Call<ApiPojo>

    @Multipart
    @POST("manage_photo")
    fun managePhoto(
        @Part("media_id") media_id: RequestBody?,
        @Part("type") type: RequestBody?,
        @Part("user_id") user_id: RequestBody?,
        @Part("image\"; filename=\"pp.png\" ") image: RequestBody?,
        @Part("dp") dp: RequestBody?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("manage_profile")
    fun manageProfile(
        @Field("user_id") user_id: String?,
        @Field("name") type: String?,
        @Field("nick_name") nick_name: String?,
        @Field("dob") dob: String?,
        @Field("location") location: String?,
        @Field("about") about: String?,
        @Field("gender") gender: String?,
        @Field("sex_orientation") sex_orientation: String?
    ): Call<ApiPojo>


    @FormUrlEncoded
    @POST("check_username")
    fun checkUsername(
        @Field("api_key") api_key: String?,
        @Field("username") username: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("update_mobile")
    fun updateMobile(
        @Field("user_id") user_id: String?,
        @Field("mobile") mobile: String?
    ): Call<ApiPojo>


    @FormUrlEncoded
    @POST("change_password")
    fun changePassword(
        @Field("user_id") user_id: String?,
        @Field("old_password") old_password: String?,
        @Field("new_password") new_password: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("update_email")
    fun updateEmail(
        @Field("user_id") user_id: String?,
        @Field("email") email: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("user_profile")
    fun getProfile(
        @Field("user_id") user_id: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("feedback_list")
    fun loadFeedback(
        @Field("user_id") user_id: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("feedback_manage")
    fun saveFeedback(
        @Field("feedback_id") feedback_id: String?,
        @Field("user_id") user_id: String?,
        @Field("subject") subject: String?,
        @Field("message") message: String?
    ): Call<ApiPojo>


    @FormUrlEncoded
    @POST("photo_delete")
    fun deleteProfilePhoto(
        @Field("user_id") user_id: String?,
        @Field("media_id") media_id: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("user_photo_list")
    fun getUserPhoto(
        @Field("user_id") user_id: String?,
        @Field("type") type: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("delete_user")
    fun deleteAccount(
        @Field("user_id") from_user_id: String?
    ): Call<ApiPojo>

    @FormUrlEncoded
    @POST("send_message")
    fun sendMessages(
        @Field("from_user_id") from_user_id: String?,
        @Field("to_user_id") to_user_id: String?,
        @Field("message") message: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("message_list")
    fun loadMessages(
        @Field("from_user_id") from_user_id: String?,
        @Field("to_user_id") to_user_id: String?
    ): Call<ApiPojoArray>


    @FormUrlEncoded
    @POST("notification_list")
    fun loadNotification(
        @Field("user_id") user_id: String?,
        @Field("search_key") search_key: String?
    ): Call<ApiPojoArray>


    @FormUrlEncoded
    @POST("profile_request_list")
    fun loadFriendRequest(
        @Field("user_id") user_id: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("message_user_list")
    fun loadChatUsers(
        @Field("user_id") user_id: String?,
        @Field("search_key") search_key: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("profile_recommendation")
    fun getProfileRecommendation(
        @Field("user_id") user_id: String?
    ): Call<ApiPojoArray>


    @FormUrlEncoded
    @POST("user_like_dislike")
    fun likeDislike(
        @Field("from_user_id") from_user_id: String?,
        @Field("to_user_id") to_user_id: String?,
        @Field("status") status: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("matching_user_list")
    fun getMatchingUsers(
        @Field("user_id") user_id: String?,
        @Field("photo_url") photo_url: String?,
        @Field("match_percent") match_percent: String?
    ): Call<ApiPojoArray>


    @FormUrlEncoded
    @POST("category_list")
    fun category_list(
        @Field("api_key") api_key: String?
    ): Call<ApiPojoArray>

    @GET("posts")
    fun post_list(
    ): Call<ArrayList<PostPojo>>

    @FormUrlEncoded
    @POST("category_item_list")
    fun category_item_list(
        @Field("api_key") api_key: String?,
        @Field("category_id") category_id: String?,
        @Field("customer_id") customer_id: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("cart_item_list")
    fun cart_item_list(
        @Field("api_key") api_key: String?,
        @Field("customer_id") customer_id: String?
    ): Call<ApiPojoArray>

    @FormUrlEncoded
    @POST("update_cart")
    fun update_cart(
        @Field("api_key") api_key: String?,
        @Field("product_id") product_id: String?,
        @Field("qty") qty: String?,
        @Field("customer_id") customer_id: String?
    ): Call<ApiPojo>


    @FormUrlEncoded
    @POST("get_cart_count")
    fun get_cart_count(
        @Field("api_key") api_key: String?,
        @Field("customer_id") customer_id: String?
    ): Call<ApiPojo>


    companion object {
        operator fun invoke(apiUrl: String): ServerApi {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            StaticMethods.log("BaseUrl",apiUrl)
            return Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServerApi::class.java)
        }
    }
}