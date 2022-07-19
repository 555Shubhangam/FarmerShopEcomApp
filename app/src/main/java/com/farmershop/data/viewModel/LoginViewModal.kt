package com.farmershop.data.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farmershop.R
import com.farmershop.data.model.request.ForgetPasswordRequest
import com.farmershop.data.model.request.LoginRequest
import com.farmershop.data.model.response.*
import com.farmershop.data.repository.LoginRepository
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.Constants
import com.farmershop.utils.Resource
import com.farmershop.utils.Utility
import com.google.firebase.crashlytics.internal.common.Utils
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by Amit Gupta on 16-05-2021.
 */
class LoginViewModal : ViewModel(){

    private val repository = LoginRepository()

    val user = MutableLiveData<Resource<Data>>()
    val forgetPassword = MutableLiveData<Resource<ForgetPasswordModal>>()
    val verifyOtp = MutableLiveData<Resource<String>>()
    val resetPassword = MutableLiveData<Resource<String>>()

   // val logout = MutableLiveData<Resource<String>>()
    /*fun logout() = viewModelScope.launch {
        if (Utility.hasInternetConnection(app.applicationContext)) {
            logout.postValue(Resource.Loading())
            val response = repository.logout()
            logout.postValue(handleLogoutResponse(response))
        } else {
            logout.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleLogoutResponse(response: Response<CommonResponse>?): Resource<String> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status) {
                    Resource.Success(res.message, res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(app.resources.getString(R.string.something_went_wrong))
    }

*/
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
                return if (res.status==Constants.HTTP_SUCCESS) {
                    res.data.let { Resource.Success(res.message, it) }
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
    }


    fun forgetPassword(request: ForgetPasswordRequest) = viewModelScope.launch {
        if (Utility.hasInternetConnection(MyApp.application)) {
            forgetPassword.postValue(Resource.Loading())
            val response = repository.forgetPassword(request)
            forgetPassword.postValue(handleForgetResponse(response))
        } else {
            forgetPassword.postValue(Resource.Error(MyApp.application.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleForgetResponse(response: Response<ForgetPasswordResponse>?): Resource<ForgetPasswordModal> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status) {
                    Resource.Success(res.message, res.data)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(MyApp.application.resources.getString(R.string.something_went_wrong))
    }


    /*fun verifyOtp(request: VerifyOtpRequest) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            verifyOtp.postValue(Resource.Loading())
            val response = repository.verifyOtp(request)
            verifyOtp.postValue(handleOtpResponse(response))
        } else {
            verifyOtp.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleOtpResponse(response: Response<CommonResponse>?): Resource<String> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status) {
                    Resource.Success(res.message,res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(app.resources.getString(R.string.something_went_wrong))
    }


    fun resetPassword(request: ResetPasswordRequest) = viewModelScope.launch {
        if (Utils.hasInternetConnection(app.applicationContext)) {
            resetPassword.postValue(Resource.Loading())
            val response = repository.resetPassword(request)
            resetPassword.postValue(handleResetPasswordResponse(response))
        } else {
            resetPassword.postValue(Resource.Error(app.resources.getString(R.string.no_internet)))
        }
    }

    private fun handleResetPasswordResponse(response: Response<CommonResponse>?): Resource<String> {
        if (response?.isSuccessful!!) {
            response.body()?.let { res ->
                return if (res.status) {
                    Resource.Success(res.message, res.message)
                } else {
                    Resource.Error(res.message)
                }
            }
        }
        return Resource.Error(app.resources.getString(R.string.something_went_wrong))
    }
*/

}