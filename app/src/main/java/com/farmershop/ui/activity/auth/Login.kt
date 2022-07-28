package com.farmershop.ui.activity.auth

import android.content.Intent
import com.farmershop.R
import com.farmershop.ui.base.BaseActivity
import com.farmershop.databinding.LoginBinding
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.farmershop.data.model.request.LoginRequest
import com.farmershop.data.viewModel.LoginViewModal
import com.farmershop.ui.activity.Home
import com.farmershop.utils.*
import kotlinx.android.synthetic.main.login.*


class Login : BaseActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var viewModal: LoginViewModal
    private var isEyeOpenNewPassword: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login)
        viewModal = ViewModelProvider(this).get(LoginViewModal::class.java)
        init()
        setObserver()
    }

    private fun init(){
        binding.tvDonTHaveAccount.setOnClickListener {
            startActivity(Intent(this@Login, Register::class.java))
            finish()
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this@Login, ForgotPasswordActivity::class.java)
                .putExtra(Constants.Email_MOBILE,et_email.text.toString()))
            //finish()

        }
        binding.tvLogin.setOnClickListener {
            //startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            if (TextUtils.isEmpty(binding.etEmail.text)) {
                StaticMethods.alert(this, getString(R.string.email_and_mobile_required))
                //Utility.showSnackBar(binding.root, getString(R.string.email_and_mobile_required))
            }/* else if (!(Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString().trim()).matches())
            ) {
                Utility.showSnackBar(binding.root, "Enter Valid Email")
            }*/ else if (TextUtils.isEmpty(binding.etPassword.text)) {
                StaticMethods.alert(this, getString(R.string.password_required))
                //Utility.showSnackBar(binding.root, getString(R.string.password_required))
            } else {
                Utility.hideKeyboard(this)
                login()
            }
        }
        binding.imgEyeNewPassword.setOnClickListener {
            if (isEyeOpenNewPassword) {
                isEyeOpenNewPassword = false
                binding.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.imgEyeNewPassword.background =
                    ContextCompat.getDrawable(this@Login, R.mipmap.icn_eye_open)
            } else {
                isEyeOpenNewPassword = true
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imgEyeNewPassword.background =
                    ContextCompat.getDrawable(this@Login, R.mipmap.icn_eye_inactive)
            }
        }
    }

    private fun login() {
           val username = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
        viewModal.login(username,password)
    }

    private fun setObserver() {
        viewModal.user.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    response.data?.token?.let { AppSession.getInstance(this).setToken(it) }
                    response.data?.id?.let { AppSession.getInstance(this).setUserId(it.toString()) }
                    //Log.d("LoginActivity", "LoginActivity setObserver: " + response.data)
                    response.data?.name?.let { AppSession.getInstance(this).setName(it) }
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@Login, Home::class.java)
                        .putExtra("username",binding.etEmail.text.toString())
                        .putExtra("purpose","login"))

                    finish()
                    /*  response.data?.email?.let { PrefManager.write(PrefManager.EMAIL, it) }
                      response.data?.phone_number?.let { PrefManager.write(PrefManager.PHONE, it) }
                      response.data?.image?.let { PrefManager.write(PrefManager.IMAGE, it) }
                      response.data?.country_id?.let { PrefManager.write(PrefManager.COUNTRY_ID, it) }
                      response.data?.country_name?.let {
                          PrefManager.write(
                              PrefManager.COUNTRY_NAME,
                              it
                          )
                      }
                      PrefManager.write(
                          PrefManager.PASSWORD,
                          binding.etPassword.text.toString().trim()
                      )*/
                     Log.d(TAG, "ressponse message --- : " + response.message.toString())
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

    override fun onBackPressed() {
      finish()
    }
}