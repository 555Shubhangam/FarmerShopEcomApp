package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ForgetPasswordRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.LoginRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModal : ViewModel(){

    private val repository = LoginRepository()

    val user = MutableLiveData<Resource<Data>>()

    fun login(username: String,password:String) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            user.postValue(Resource.Loading())
            val response = repository.signIn(username,password)
            user.postValue(handleLoginResponse(response))
        } else {
            user.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>?): Resource<Data> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status) {
                    res.data.let { Resource.Success(res.message, it) }
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(response.message())
    }
}