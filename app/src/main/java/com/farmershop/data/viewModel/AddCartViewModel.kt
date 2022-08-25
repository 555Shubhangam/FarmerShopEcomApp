package com.farmershop.data.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.AddCartRequest
import com.farmershop.data.model.request.RegisterRequest
import com.farmershop.data.model.response.AddCartResponse
import com.farmershop.data.model.response.DataRegister
import com.farmershop.data.model.response.RegisterResponse
import com.farmershop.data.repository.AddCartRepository
import com.farmershop.data.repository.RegisterRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class AddCartViewModel : ViewModel(){

    private val repository = AddCartRepository()

    val addCartResponse = MutableLiveData<Resource<AddCartResponse>>()

    fun addCart(request: AddCartRequest) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            addCartResponse.postValue(Resource.Loading())
            val response = repository.addCart(request)
            addCartResponse.postValue(handleResponse(response))
        } else {
            addCartResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleResponse(response: Response<AddCartResponse>?): Resource<AddCartResponse> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status!!) {
                    res.let { Resource.Success(res.message!!, it) }
                } else {
                    Resource.Error(res.message!!,res)
                }
            }
        }
        return Resource.Error(response.message())
    }
}