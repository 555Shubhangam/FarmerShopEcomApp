package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.ProfileViewRepository
import com.farmershop.data.repository.ResetPasswordRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModal : ViewModel(){

    private val repository = ProfileViewRepository()
    val profileViewResponse = MutableLiveData<Resource<DataProfile>>()

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
}