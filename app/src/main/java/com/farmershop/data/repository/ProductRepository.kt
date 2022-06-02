package com.farmershop.data.repository

import AppConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import com.farmershop.data.network.PostPojo
import com.farmershop.data.network.ServerApi
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {

    fun cartItemList(customer_id:String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.productApi).cart_item_list(AppConfig.api_key,customer_id).enqueue(object : Callback<ApiPojoArray> {
            override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                Log.e("login", t.message)
            }
            override fun onResponse(call: Call<ApiPojoArray>, response: Response<ApiPojoArray>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body();

                } else {
                    Log.e("login", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun categoryItemList(category_id:String,customer_id:String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.productApi).category_item_list(AppConfig.api_key,category_id,customer_id).enqueue(object : Callback<ApiPojoArray> {
            override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                Log.e("login", t.message)
            }
            override fun onResponse(call: Call<ApiPojoArray>, response: Response<ApiPojoArray>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body();

                } else {
                    Log.e("login", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun categoryList(): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.productApi).category_list(AppConfig.api_key).enqueue(object : Callback<ApiPojoArray> {
            override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                Log.e("login", t.message)
            }
            override fun onResponse(call: Call<ApiPojoArray>, response: Response<ApiPojoArray>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body();

                } else {
                    Log.e("login", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun updateCart(product_id:String,qty:String,customer_id:String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.productApi).update_cart(AppConfig.api_key,product_id,qty,customer_id).enqueue(object : Callback<ApiPojo> {
            override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                Log.e("login", t.message)
            }
            override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body();

                } else {
                    Log.e("login", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun getHomeCounter(customer_id:String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.productApi).get_cart_count(AppConfig.api_key,customer_id).enqueue(object : Callback<ApiPojo> {
            override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                Log.e("login", t.message)
            }
            override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body();

                } else {
                    Log.e("login", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }


    fun postList(): LiveData<ArrayList<PostPojo>> {
        val apiResponse = MutableLiveData<ArrayList<PostPojo>>()
        ServerApi(AppConfig.demoApi).post_list().enqueue(object : Callback<ArrayList<PostPojo>> {
            override fun onFailure(call: Call<ArrayList<PostPojo>>, t: Throwable) {
                Log.e("login", t.message)
            }
            override fun onResponse(call: Call<ArrayList<PostPojo>>, response: Response<ArrayList<PostPojo>>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body();

                } else {
                    Log.e("login", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

}