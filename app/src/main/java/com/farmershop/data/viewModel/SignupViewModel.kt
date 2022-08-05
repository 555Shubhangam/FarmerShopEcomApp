package com.farmershop.data.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.RegisterRequest
import com.farmershop.data.model.response.DataRegister
import com.farmershop.data.model.response.RegisterResponse
import com.farmershop.data.repository.RegisterRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class SignupViewModel : ViewModel(){

    private val repository = RegisterRepository()

    val user = MutableLiveData<Resource<DataRegister>>()

    fun register(request: RegisterRequest) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            user.postValue(Resource.Loading())
            val response = repository.register(request)
            user.postValue(handleResponse(response))
        } else {
            user.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleResponse(response: Response<RegisterResponse>?): Resource<DataRegister> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status!!) {
                    res.data!!.let { Resource.Success(res.message!!, it) }
                } else {
                    Resource.Error(res.message!!)
                }
            }
        }
        return Resource.Error(response.message())
    }
}