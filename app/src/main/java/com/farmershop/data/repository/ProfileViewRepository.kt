package com.farmershop.data.repository

import com.farmershop.data.model.request.UpdateProfileRequest
import com.farmershop.data.network.RetrofitBuilder
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewRepository {
    suspend fun viewProfile() =
        RetrofitBuilder.apiService?.profileView()

    suspend fun getState() =
        RetrofitBuilder.apiService?.getState()

    suspend fun updateProfile(userName:String,request:UpdateProfileRequest) =
        RetrofitBuilder.apiService?.updateProfile(userName,request)

    suspend fun updateProfilePhoto(userName:RequestBody,imagePart: MultipartBody.Part) =
        RetrofitBuilder.apiService?.updateProfilePhoto(userName,imagePart)
}