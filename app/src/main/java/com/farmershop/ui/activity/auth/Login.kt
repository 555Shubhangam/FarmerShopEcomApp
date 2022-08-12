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
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import com.farmershop.data.model.request.LoginRequest
import com.farmershop.data.viewModel.LoginViewModal
import com.farmershop.databinding.ItemErrorBinding
import com.farmershop.ui.activity.Home
import com.farmershop.ui.base.MyApp
import com.farmershop.utils.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.login.et_email
import kotlinx.android.synthetic.main.login.rvError
import kotlinx.android.synthetic.main.register.*


class Login : BaseActivity() {
    private lateinit var binding: LoginBinding
    private lateinit var viewModal: LoginViewModal
    private var isEyeOpenNewPassword: Boolean = false
    var errorList = ArrayList<String>()
    private var rvAdapError: RecyclerViewGenricAdapter<String, ItemErrorBinding>? = null
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
                rvError.visibility=View.GONE
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
                    rvError.visibility=View.GONE
                    response.data?.data?.token?.let { AppSession.setToken(it) }
                    val token = AppSession.getToken()
                    Log.wtf("dddsdsdsdsdd", "token --- $token")
                    response.data?.data?.id?.let { AppSession.setUserId(it.toString()) }
                    response.data?.data?.name?.let { AppSession.setName(it) }
                    response.data?.data?.email?.let { AppSession.setEmail(it) }
                    response.data?.data?.mobile?.let { AppSession.setMobile(it) }
                    response.data?.data?.photo?.let { AppSession.setPhoto(it) }
                    response.data?.data?.username?.let { AppSession.setUserName(it) }
                    response.data?.data?.gender?.let { AppSession.setGender(it) }
                    response.data?.data?.aadhar_no?.let { AppSession.setAadhaarNo(it.toString()) }
                    response.data?.data?.address?.let { AppSession.setAddress(it) }
                    response.data?.data?.dob?.let { AppSession.setDob(it) }
                    response.data?.data?.zip?.let { AppSession.setZip(it.toString()) }
                    response.data?.data?.state_id?.let { AppSession.setStateID(it.toString()) }
                    response.data?.data?.state_name?.let { AppSession.setState(it) }
                    response.data?.data?.city?.let { AppSession.setCity(it) }
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@Login, Home::class.java)
                        .putExtra("username",binding.etEmail.text.toString())
                        .putExtra("purpose","login"))
                    finish()
                     Log.wtf(TAG, "ressponse message --- : " + response.message.toString())
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    if(response.data?.errorsList!=null){
                        errorList = response.data?.errorsList!!
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
                    Log.wtf(TAG, "ressponse message --- : " + response.message.toString())
                }
            }
        }
    }

    override fun onBackPressed() {
      finish()
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