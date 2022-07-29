package com.farmershop.ui.activity.setting

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
import com.farmershop.data.model.request.ChangePasswordRequest
import com.farmershop.data.model.request.ResetPasswordRequest
import com.farmershop.data.viewModel.ChangePasswordViewModal
import com.farmershop.data.viewModel.ResetPasswordViewModal
import com.farmershop.databinding.ActivityChangePasswordBinding
import com.farmershop.databinding.ActivityResetPasswordBinding
import com.farmershop.ui.activity.Home
import com.farmershop.utils.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangePasswordBinding
    private lateinit var viewModal: ChangePasswordViewModal

    private var isEyeOpenCnfPassword: Boolean = false
    private var isEyeOpenNewPassword: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password)
        viewModal = ViewModelProvider(this@ChangePasswordActivity).get(ChangePasswordViewModal::class.java)
        init()
        setObserver()
    }

    private fun init() {
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.change_password)


        binding.imgEyeCnfPassword.setOnClickListener {
            if (isEyeOpenCnfPassword) {
                isEyeOpenCnfPassword = false
                binding.etCnfPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.imgEyeCnfPassword.background =
                    ContextCompat.getDrawable(this@ChangePasswordActivity, R.mipmap.icn_eye_open)
            } else {
                isEyeOpenCnfPassword = true
                binding.etCnfPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imgEyeCnfPassword.background =
                    ContextCompat.getDrawable(this@ChangePasswordActivity, R.mipmap.icn_eye_inactive)
            }
        }

        binding.imgEyeNewPassword.setOnClickListener {
            if (isEyeOpenNewPassword) {
                isEyeOpenNewPassword = false
                binding.etNewPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.imgEyeNewPassword.background =
                    ContextCompat.getDrawable(this@ChangePasswordActivity, R.mipmap.icn_eye_open)
            } else {
                isEyeOpenNewPassword = true
                binding.etNewPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imgEyeNewPassword.background =
                    ContextCompat.getDrawable(this@ChangePasswordActivity, R.mipmap.icn_eye_inactive)
            }
        }

        binding.tvReSetPassword.setOnClickListener {
            Utility.hideKeyboard(this)
            validation()
        }
    }
    private fun validation() {
        if(Validation.isEmpty(binding.etOldPassword.text.toString())){
            StaticMethods.alert(this, getString(R.string.old_password_required))
        } else if(Validation.isEmpty(binding.etNewPassword.text.toString())){
            StaticMethods.alert(this, getString(R.string.password_required))
        } else if(Validation.isEmpty(binding.etCnfPassword.text.toString())){
            StaticMethods.alert(this, getString(R.string.cnf_password_required))
        } else if(binding.etCnfPassword.text.toString() != binding.etNewPassword.text.toString()){
            StaticMethods.alert(this, getString(R.string.password_not_matched))
        } else{
            Utility.hideKeyboard(this)
            resetPassword()
        }
    }

    private fun resetPassword() {
        val oldpassword = binding.etOldPassword.text.toString()
        val password = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etCnfPassword.text.toString()
        val request = ChangePasswordRequest(oldpassword,password,confirmPassword)
        viewModal.resetPassword(request)
    }

    private fun setObserver() {
        viewModal.changePasswordResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ",response.message.toString())

            when (response) {

                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()

                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Home::class.java))
                    Log.d("ressponsexcxmessage ",response.message.toString())
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