package com.farmershop.data.network

import android.util.Log
import com.farmershop.BuildConfig
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.AppSession
import com.farmershop.utils.AuthInterceptor
import com.farmershop.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private lateinit var retrofit: Retrofit

    private fun getRetrofitInstance(): Retrofit? {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            // development build
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            // production build
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        val client = OkHttpClient.Builder()
           /* .addInterceptor(logging)
            .addInterceptor { chain: Interceptor.Chain ->

                val token = AppSession.getInstance(MyApp.application).getToken()
                Log.e("authToken->",token!!)
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", token.trim())
                    .method(original.method, original.body).build()
                chain.proceed(request)
            }*/
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build()

        if (!(::retrofit.isInitialized)) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }

    val apiService: ApiService? = getRetrofitInstance()?.create(ApiService::class.java)

}