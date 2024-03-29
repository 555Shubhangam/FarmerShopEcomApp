package com.farmershop.ui.activity.auth

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import com.farmershop.data.model.request.RegisterRequest
import com.farmershop.data.viewModel.SignupViewModel
import com.farmershop.databinding.ItemErrorBinding
import com.farmershop.databinding.RegisterBinding
import com.farmershop.utils.*
import com.farmershop.ui.base.BaseActivityUser
import kotlinx.android.synthetic.main.activity_my_order.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.register.*

class Register : BaseActivityUser() {
    private val Tag = "Register"
    lateinit var binding: RegisterBinding
    private var isEyeOpenCnfPassword: Boolean = false
    private var isEyeOpenNewPassword: Boolean = false
    private lateinit var viewModal: SignupViewModel
    var errorList = ArrayList<String>()
    private var rvAdapError: RecyclerViewGenricAdapter<String, ItemErrorBinding>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.register)
        viewModal = ViewModelProvider(this).get(SignupViewModel::class.java)
        init()
        setObserver()
    }

    private fun init() {
        binding.tvSignup.setOnClickListener {
            Utility.hideKeyboard(this)
            validation()
            rvError.visibility=View.GONE
        }
        binding.imgEyeCnfPassword.setOnClickListener {
            if (isEyeOpenCnfPassword) {
                isEyeOpenCnfPassword = false
                binding.etCnfPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.imgEyeCnfPassword.background =
                    ContextCompat.getDrawable(this@Register, R.mipmap.icn_eye_open)
            } else {
                isEyeOpenCnfPassword = true
                binding.etCnfPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imgEyeCnfPassword.background =
                    ContextCompat.getDrawable(this@Register, R.mipmap.icn_eye_inactive)
            }
        }
        binding.tvAlreadyHaveAnAccount.setOnClickListener {
            startActivity(Intent(this@Register, Login::class.java))
            finish()
        }
        binding.imgEyeNewPassword.setOnClickListener {
            if (isEyeOpenNewPassword) {
                isEyeOpenNewPassword = false
                binding.etNewPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.imgEyeNewPassword.background =
                    ContextCompat.getDrawable(this@Register, R.mipmap.icn_eye_open)
            } else {
                isEyeOpenNewPassword = true
                binding.etNewPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.imgEyeNewPassword.background =
                    ContextCompat.getDrawable(this@Register, R.mipmap.icn_eye_inactive)
            }
        }



    }

    private fun setObserver() {
        viewModal.user.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    rvError.visibility=View.GONE
                    //response.data?.token?.let { AppSession.getInstance(this).setToken(it) }
                    //response.data?.id?.let { AppSession.getInstance(this).setUserId(it.toString()) }
                    Log.d("RESGGGG", "LoginActivity setObserver: " + response.message)
                    //response.data?.name?.let { AppSession.getInstance(this).setName(it) }
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@Register, Login::class.java))
                    finish()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    Log.d("RESGGGG", "LoginActivity setObserver: " + response.data?.errors)
                    if(response.data?.errors!=null){
                        Log.d("RESGGGG", "LoginActivity setObserver: " + response.message)
                        errorList = response.data?.errors!!
                    rvError.visibility=View.VISIBLE
                    rvError.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    setErrorListAdapter(rvError)
                    }
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

    private fun register() {
        val name = binding.etName.text.toString()
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val mobile = binding.etMobile.text.toString()
        val address = binding.etAddress.text.toString()
        val password = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etCnfPassword.text.toString()
        val request = RegisterRequest(name,address,mobile,username,email,password,confirmPassword)
        viewModal.register(request)
    }

    private fun validation() {
        if(Validation.isEmpty(binding.etName.text.toString())){
            StaticMethods.alert(this, getString(R.string.name_required))
        } else if(Validation.isEmpty(binding.etUsername.text.toString())){
            StaticMethods.alert(this, getString(R.string.username_required))
        } else if(Validation.isEmpty(binding.etEmail.text.toString())){
            StaticMethods.alert(this, getString(R.string.email_required))
        } else if(!Validation.isValidEmail(binding.etEmail.text.toString())){
            StaticMethods.alert(this, getString(R.string.valid_email_required))
        } else if(Validation.isEmpty(binding.etMobile.text.toString())){
            StaticMethods.alert(this, getString(R.string.mobile_required))
        } else if(!Validation.isValidMobile(binding.etMobile.text.toString())){
            StaticMethods.alert(this, getString(R.string.valid_mobile_number_required))
        } else if(Validation.isEmpty(binding.etAddress.text.toString())){
            StaticMethods.alert(this, getString(R.string.address_required))
        } else if(Validation.isEmpty(binding.etNewPassword.text.toString())){
            StaticMethods.alert(this, getString(R.string.password_required))
        } else if(Validation.isEmpty(binding.etCnfPassword.text.toString())){
            StaticMethods.alert(this, getString(R.string.cnf_password_required))
        } else if(binding.etCnfPassword.text.toString() != binding.etNewPassword.text.toString()){
            StaticMethods.alert(this, getString(R.string.password_not_matched))
        } else{
            Utility.hideKeyboard(this)
            register()
        }
    }


    fun setErrorListAdapter(recyclerView: RecyclerView) {
        rvAdapError  = RecyclerViewGenricAdapter<String, ItemErrorBinding>(
            errorList,
            R.layout.item_error, object :
                RecyclerCallback<ItemErrorBinding, String> {
                override fun bindData(
                    binder: ItemErrorBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {
                        tvError.text = model
                    }
                }
            })
        recyclerView.adapter = rvAdapError

    }
}