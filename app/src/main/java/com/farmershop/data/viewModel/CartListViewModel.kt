package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.CartListRepository
import com.farmershop.data.repository.ResetPasswordRepository
import com.farmershop.data.repository.SearchRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class CartListViewModel : ViewModel(){

    private val repository = CartListRepository()
    val cartListResponse = MutableLiveData<Resource<CartListResponse>>()

    fun cartList(customerId: Int) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            cartListResponse.postValue(Resource.Loading())
            val response = repository.getCartList(customerId)
            cartListResponse.postValue(handleSearchProductResponse(response))
        } else {
            cartListResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleSearchProductResponse(response: Response<CartListResponse>?): Resource<CartListResponse> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status){
                    Resource.Success(res.message, res)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        //return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
        return Resource.Error(response.message())
    }
}