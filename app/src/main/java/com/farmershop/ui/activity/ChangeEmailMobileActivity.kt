package com.farmershop.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.farmershop.R
import com.farmershop.data.model.request.ChangeEmailMobileRequest
import com.farmershop.data.viewModel.ChangeEmailMobileViewModal
import com.farmershop.data.viewModel.ForgotPasswordViewModal
import com.farmershop.databinding.ActivityChangeEmailMobileBinding
import com.farmershop.databinding.ActivityForgotPasswordBinding
import com.farmershop.ui.activity.auth.OTP
import com.farmershop.utils.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ChangeEmailMobileActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangeEmailMobileBinding
    private lateinit var viewModal: ChangeEmailMobileViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_email_mobile)
        viewModal = ViewModelProvider(this@ChangeEmailMobileActivity).get(ChangeEmailMobileViewModal::class.java)

        init()
        setObserver()
    }
    private fun init() {
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.change_mobile_email)


        binding.tvForgotPassword.setOnClickListener {
            Utility.hideKeyboard(this)
            validation()
        }
    }
    private fun validation() {
        if(Validation.isEmpty(binding.etEmailMobile.text.toString())){
            StaticMethods.alert(this, getString(R.string.email_and_mobile_required))
        }else if(!Validation.isValidEmailOrMobile(binding.etEmailMobile.text.toString())){
            StaticMethods.alert(this, getString(R.string.valid_email_mobile_required))
        }   else{
            Utility.hideKeyboard(this)
            changeEmailMobileApi()
        }
    }
    private fun changeEmailMobileApi() {
        if(Validation.isValidEmail(binding.etEmailMobile.text.toString())){
            val request = ChangeEmailMobileRequest(binding.etEmailMobile.text.toString(),"")
            viewModal.changeEmailMobile(AppSession.getUserName().toString(),request)
        }else{
            val request = ChangeEmailMobileRequest("",binding.etEmailMobile.text.toString())
            viewModal.changeEmailMobile(AppSession.getUserName().toString(),request)
        }
    }

    private fun setObserver() {
        viewModal.changeEmailMobileResponse.observe(this) { response ->

            when (response) {

                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()

                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    var otpFor = if(Validation.isValidEmail(binding.etEmailMobile.text.toString())){
                        "email"
                    }else{
                        "mobile"
                    }
                    startActivity(
                        Intent(this, OTP::class.java)
                        .putExtra(Constants.USER_NAME,etusername.text.toString())
                        .putExtra(Constants.AUTH_TYPE,"ChangeEmailMobile")
                        .putExtra(Constants.OTP_VERIFY_FOR,otpFor))
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}