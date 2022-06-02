package com.farmershop.data.repository

import AppConfig
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import com.farmershop.data.network.ServerApi
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {



    fun login(username: String, password: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        Log.e("username",username)
        Log.e("password",password)

        ServerApi(AppConfig.authApi).login(AppConfig.api_key,username, password).enqueue(object : Callback<ApiPojo> {
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

    fun registerUser(mobile:String,email:String,name:String,password:String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).registerUser(AppConfig.api_key,mobile, email,name,password).enqueue(object : Callback<ApiPojo> {
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

    fun otpLogin(username: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).otpLogin(AppConfig.api_key,username).enqueue(object : Callback<ApiPojo> {
            override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                Log.e("otpLogin", t.message)
            }
            override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body()
                } else {
                    Log.e("otpLogin", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun resetPassword(username: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).resetPassword(username).enqueue(object : Callback<ApiPojo> {
            override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                Log.e("resetPassword", t.message)
            }
            override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body()
                } else {
                    Log.e("resetPassword", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun createPassword(user_id: String, password: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).createPassword(user_id, password)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("createPassword", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("createPassword", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun verifyEmailOTP(user_id: String, otp: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).verifyEmailOTP(user_id, otp)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("verifyEmailOTP", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("verifyEmailOTP", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun sendEmailOTP(username: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).sendEmailOTP(username).enqueue(object : Callback<ApiPojo> {
            override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                Log.e("sendEmailOTP", t.message)
            }

            override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body()
                } else {
                    Log.e("sendEmailOTP", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun manageProfilePhoto(
        user_id: RequestBody?,
        image: RequestBody?
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).manageProfilePhoto(user_id, image)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("manageProfilePhoto", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("manageProfilePhoto", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun managePhoto(
        media_id: RequestBody?,
        type: RequestBody?,
        user_id: RequestBody?,
        image: RequestBody?,
        dp: RequestBody?
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).managePhoto(media_id, type, user_id, image, dp)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("managePhoto", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("managePhoto", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun manageProfile(
        user_id: String,
        name: String,
        nick_name: String,
        dob: String,
        location: String,
        about: String,
        gender: String,
        sex_orientation: String
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).manageProfile(
            user_id,
            name,
            nick_name,
            dob,
            location,
            about,
            gender,
            sex_orientation
        )
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("manageProfile", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        Log.e("manageProfile", Gson().toJson(response.body()))
                        apiResponse.value = response.body()
                    } else {
                        Log.e("manageProfile", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun updateMobile(
        user_id: String,
        mobile: String
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).updateMobile(user_id, mobile)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("updateMobile", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("updateMobile", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun updateEmail(
        user_id: String,
        email: String
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).updateEmail(user_id, email)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("updateEmail", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("updateEmail", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }


    fun checkUsername(username: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).checkUsername(AppConfig.api_key,username)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("checkUsername", t.message)
                }
                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("checkUsername", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }


    fun saveFeedback(args: Array<String>): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).saveFeedback(args[0], args[1], args[2], args[3])
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("saveFeedback", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("saveFeedback", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun loadFeedback(user_id: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).loadFeedback(user_id).enqueue(object : Callback<ApiPojoArray> {
            override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                Log.e("loadFeedback", t.message)
            }

            override fun onResponse(call: Call<ApiPojoArray>, response: Response<ApiPojoArray>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body()
                } else {
                    Log.e("loadFeedback", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun getProfile(user_id: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).getProfile(user_id).enqueue(object : Callback<ApiPojo> {
            override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                Log.e("getProfile", t.message)
            }

            override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                if (response.isSuccessful) {
                    apiResponse.value = response.body()
                } else {
                    Log.e("getProfile", "Api not accessible: Code :" + response.code())
                }
            }
        }
        )
        return apiResponse
    }

    fun deleteProfilePhoto(user_id: String, media_id: String): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).deleteProfilePhoto(user_id, media_id)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("deleteProfilePhoto", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("deleteProfilePhoto", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }


    fun getProfileRecommendation(user_id: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).getProfileRecommendation(user_id)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("profileRecommendation", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e(
                            "profileRecommendation",
                            "Api not accessible: Code :" + response.code()
                        )
                    }
                }
            }
            )
        return apiResponse
    }

    fun deleteAccount(
        user_id: String
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).deleteAccount(user_id)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("deleteAccount", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojo>,
                    response: Response<ApiPojo>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("deleteAccount", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }


    fun sendMessages(
        from_user_id: String,
        to_user_id: String,
        message: String
    ): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).sendMessages(from_user_id, to_user_id, message)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("sendMessages", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("sendMessages", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun loadMessages(from_user_id: String, to_user_id: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).loadMessages(from_user_id, to_user_id)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("loadMessages", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("loadMessages", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun loadNotification(user_id: String, searchKey: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).loadNotification(user_id, searchKey)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("loadNotification", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("loadNotification", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun loadFriendRequest(user_id: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).loadFriendRequest(user_id)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("loadChatUsers", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("loadChatUsers", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun loadChatUsers(user_id: String, searchKey: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).loadChatUsers(user_id, searchKey)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("loadChatUsers", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("loadChatUsers", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun getUserPhoto(user_id: String, type: String): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).getUserPhoto(user_id, type)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("getUserPhoto", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        Log.e("getUserPhoto", Gson().toJson(response.body()).toString())
                        apiResponse.value = response.body()
                    } else {
                        Log.e("getUserPhoto", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun likeDislike(
        from_user_id: String,
        to_user_id: String,
        status: String
    ): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(AppConfig.authApi).likeDislike(from_user_id, to_user_id, status)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("likeDislike", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        Log.e("likeDislike", Gson().toJson(response.body()))
                        apiResponse.value = response.body()
                    } else {
                        Log.e("likeDislike", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }


    fun getMatchingUsers(
        api: String,
        user_id: String,
        photo_url: String,
        match_percent: String
    ): LiveData<ApiPojoArray> {
        val apiResponse = MutableLiveData<ApiPojoArray>()
        ServerApi(api).getMatchingUsers(user_id, photo_url, match_percent)
            .enqueue(object : Callback<ApiPojoArray> {
                override fun onFailure(call: Call<ApiPojoArray>, t: Throwable) {
                    Log.e("getMatchingUsers", t.message)
                }

                override fun onResponse(
                    call: Call<ApiPojoArray>,
                    response: Response<ApiPojoArray>
                ) {
                    if (response.isSuccessful) {
                        Log.e("getMatchingUsers", Gson().toJson(response.body()))
                        apiResponse.value = response.body()
                    } else {
                        Log.e("getMatchingUsers", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }

    fun changePassword(
        user_id: String,
        old_password: String,
        new_password: String
    ): LiveData<ApiPojo> {
        val apiResponse = MutableLiveData<ApiPojo>()
        ServerApi(AppConfig.authApi).changePassword(user_id, old_password, new_password)
            .enqueue(object : Callback<ApiPojo> {
                override fun onFailure(call: Call<ApiPojo>, t: Throwable) {
                    Log.e("changePassword", t.message)
                }

                override fun onResponse(call: Call<ApiPojo>, response: Response<ApiPojo>) {
                    if (response.isSuccessful) {
                        apiResponse.value = response.body()
                    } else {
                        Log.e("changePassword", "Api not accessible: Code :" + response.code())
                    }
                }
            }
            )
        return apiResponse
    }


}