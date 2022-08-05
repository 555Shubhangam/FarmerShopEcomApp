package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ChangePasswordRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.model.request.UpdateProfileRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.ProfileViewRepository
import com.farmershop.data.repository.ResetPasswordRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ProfileViewModal : ViewModel(){

    private val repository = ProfileViewRepository()
    val profileViewResponse = MutableLiveData<Resource<DataProfile>>()
    val updateProfileResponse = MutableLiveData<Resource<String>>()
    val updateProfilePhotoResponse = MutableLiveData<Resource<String>>()
    val stateResponse = MutableLiveData<Resource<ArrayList<DataState>>>()

    fun profileView() = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            profileViewResponse.postValue(Resource.Loading())
            val response = repository.viewProfile()
            profileViewResponse.postValue(handleProfileViewResponse(response))
        } else {
            profileViewResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleProfileViewResponse(response: Response<ProfileViewResponse>?): Resource<DataProfile> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status!!){
                    Resource.Success(res.message!!, res.data!!)
                } else {
                    Resource.Error(res.message!!)
                }
            }
        }
        //return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
        return Resource.Error(response.message())
    }

    fun getState() = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            stateResponse.postValue(Resource.Loading())
            val response = repository.getState()
            stateResponse.postValue(handleStateResponse(response))
        } else {
            profileViewResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleStateResponse(response: Response<StateResponse>?): Resource<ArrayList<DataState>> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status!!){
                    Resource.Success(res.message!!, res.data!!)
                } else {
                    Resource.Error(res.message!!)
                }
            }
        }
        //return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
        return Resource.Error(response.message())
    }

    fun updateProfile(username:String,request: UpdateProfileRequest) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            updateProfileResponse.postValue(Resource.Loading())
            val response = repository.updateProfile(username,request)
            updateProfileResponse.postValue(handleUpdateProfileResponse(response))
        } else {
            updateProfileResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleUpdateProfileResponse(response: Response<CommonResponse>?): Resource<String> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status){
                    Resource.Success(res.message, res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        //return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
        return Resource.Error(response.message())
    }

    fun updateProfilePhoto(username:RequestBody,imagePart: MultipartBody.Part) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            updateProfilePhotoResponse.postValue(Resource.Loading())
            val response = repository.updateProfilePhoto(username,imagePart)
            updateProfilePhotoResponse.postValue(handleUpdateProfilePhotoResponse(response))
        } else {
            updateProfilePhotoResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleUpdateProfilePhotoResponse(response: Response<CommonResponse>?): Resource<String> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status){
                    Resource.Success(res.message, res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        //return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
        return Resource.Error(response.message())
    }
}