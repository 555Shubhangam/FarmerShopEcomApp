package com.farmershop.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.HomeRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModal : ViewModel(){

    private val repository = HomeRepository()
    val bannerResponse = MutableLiveData<Resource<BannerResponse>>()

    fun getBanner() = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            bannerResponse.postValue(Resource.Loading())
            val response = repository.getBanner()
            bannerResponse.postValue(handleGetBannerResponse(response))
        } else {
            bannerResponse.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleGetBannerResponse(response: Response<BannerResponse>?): Resource<BannerResponse> {
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