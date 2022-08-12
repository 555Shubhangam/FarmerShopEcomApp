package com.farmershop.ui.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.farmershop.R
import com.farmershop.data.model.request.ForgotPasswordRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.viewModel.ForgotPasswordViewModal
import com.farmershop.data.viewModel.ResetPasswordViewModal
import com.farmershop.databinding.ActivityForgotPasswordBinding
import com.farmershop.databinding.ActivityResetPasswordBinding
import com.farmershop.ui.activity.Home
import com.farmershop.utils.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var viewModal: ForgotPasswordViewModal
    var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        viewModal =
            ViewModelProvider(this@ForgotPasswordActivity).get(ForgotPasswordViewModal::class.java)

        init()
        setObserver()
    }

    private fun init() {
        username = intent.getStringExtra(Constants.Email_MOBILE)!!
        etusername.setText(username)

        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.forgot_password)


        binding.tvForgotPassword.setOnClickListener {
            Utility.hideKeyboard(this)
            validation()
        }
    }

    private fun validation() {
        if (Validation.isEmpty(binding.etusername.text.toString())) {
            StaticMethods.alert(this, getString(R.string.username_required))
        } else {
            Utility.hideKeyboard(this)
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        username = binding.etusername.text.toString()
        // val request = ForgotPasswordRequest(username)
        val otpFor = when {
            Validation.isValidEmail(etusername.text.toString()) -> {
                "email"
            }
            Validation.isValidMobile(etusername.text.toString()) -> {
                "mobile"
            }
            else -> {
                "both"
            }
        }
        viewModal.forgotPassword(username, otpFor)
    }

    private fun setObserver() {
        viewModal.forgotPasswordResponse.observe(this) { response ->

            when (response) {

                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()

                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    val otpFor = when {
                        Validation.isValidEmail(etusername.text.toString()) -> {
                            "email"
                        }
                        Validation.isValidMobile(etusername.text.toString()) -> {
                            "mobile"
                        }
                        else -> {
                            "both"
                        }
                    }
                    startActivity(
                        Intent(this, OTP::class.java)
                            .putExtra(Constants.USER_NAME, etusername.text.toString())
                            .putExtra(Constants.AUTH_TYPE, "ForgotPassword")
                            .putExtra(Constants.OTP_VERIFY_FOR, otpFor)
                    )
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