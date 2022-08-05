package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ChangeEmailMobileRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.ChangeEmailMobileRepository
import com.farmershop.data.repository.ForgotPasswordRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class ChangeEmailMobileViewModal : ViewModel(){

    private val repository = ChangeEmailMobileRepository()
    val changeEmailMobileResponse = MutableLiveData<Resource<String>>()

    fun changeEmailMobile(username: String,request: ChangeEmailMobileRequest) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            changeEmailMobileResponse.postValue(Resource.Loading())
            val response = repository.changeEmailMobile(username,request)
            changeEmailMobileResponse.postValue(handleChangeEmailMobileResponse(response))
        } else {
            changeEmailMobileResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleChangeEmailMobileResponse(response: Response<CommonResponse>?): Resource<String> {
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