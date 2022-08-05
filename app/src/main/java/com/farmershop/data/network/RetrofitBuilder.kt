package com.farmershop.data.network

import android.util.Log
import com.farmershop.BuildConfig
import com.farmershop.utils.AppSession
import com.farmershop.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    private lateinit var retrofit: Retrofit

   /* private fun getRetrofitInstance(): Retrofit? {

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

   *//*     val client = OkHttpClient.Builder()
           *//**//* .addInterceptor(logging)
            .addInterceptor { chain: Interceptor.Chain ->

                val token = AppSession.getInstance(MyApp.application).getToken()
                Log.e("authToken->",token!!)
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", token.trim())
                    .method(original.method, original.body).build()
                chain.proceed(request)
            }*//**//*
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build()*//*
        val token = AppSession.getToken()
        try {
            Log.wtf("dddsdsdsdsdd", "token --- $token")
        }catch (e:Exception){}
        val  client: OkHttpClient = if (token!=null || token!="" ||token!="null") {
            Log.wtf("dddsdsdsdsdd", "tokenIF --- $token")
            OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                 val original = chain.request()
                 val builder = original.newBuilder()
                 //  if (token != null || token != "")
                 builder.addHeader("Authorization", "Bearer $token")
                 val request = builder.build()
                 chain.proceed(request)
             }).build()
         }else{
            Log.wtf("dddsdsdsdsdd", "tokenElse --- $token")
            OkHttpClient.Builder()
                 .connectTimeout(2, TimeUnit.MINUTES)
                 .readTimeout(2, TimeUnit.MINUTES)
                 .build()
         }
        if (!(::retrofit.isInitialized)) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }*/
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
           .addInterceptor(logging)
           .addInterceptor { chain: Interceptor.Chain ->

               val token = AppSession.getToken()
               try {
                   Log.wtf("dddsdsdsdsdd", "token --- $token")
               }catch (e:Exception){}
               val original = chain.request()
               val request = original.newBuilder()
                   .header("Content-Type", "application/json")
                   .header("Authorization", "Bearer $token")
                   .method(original.method, original.body).build()
               chain.proceed(request)
           }
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