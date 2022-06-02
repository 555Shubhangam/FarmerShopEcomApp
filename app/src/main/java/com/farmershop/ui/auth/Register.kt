package com.farmershop.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.farmershop.R
import com.farmershop.appSDK.*
import com.farmershop.data.network.ApiPojo
import com.farmershop.ui.home.Home
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.register.*

class Register : BaseActivityUser() {
    lateinit var viewModel: AuthViewModel
    var username = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        init()
    }

    private fun init() {
        viewModel=ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel.baseInterface=this
        StaticMethods.readyOnlyEditText(mobile)
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username").toString()
            mobile.setText(username)
        }
        btnSubmit.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        if (!Validation.isEmpty(name.text.toString())) {
            if (!Validation.isEmpty(password.text.toString())) {
                if (Validation.isValidMobile(mobile.text.toString())) {
                    if (Validation.isValidEmail(email.text.toString())) {
                        viewModel.registerUser(mobile.text.toString(),email.text.toString(),name.text.toString(),password.text.toString())
                    } else {
                        StaticMethods.alert(this, "Valid email id is required")
                    }
                } else {
                    StaticMethods.alert(this, "Valid mobile number is required")
                }
            } else {
                StaticMethods.alert(this, "Password is required")
            }
        } else {
            StaticMethods.alert(this, "Name is required")
        }
    }

    //-----------API Response------------
    override fun onApiStart() {
        progressBar.show()
    }

    override fun onApiSuccess(apiResponse: LiveData<ApiPojo>, responseFlag: String?) {
        apiResponse.observe(this, Observer {
            progressBar.hide()
            val response = apiResponse.value!!
            val message = response.message.toString()
            toast(message)
            if (response.status.toString() == "1") {
                toast(message)
                if (responseFlag == "Register") {
                    val data = response.data
                    StaticMethods.setUserSession(applicationContext,data!!)
                    startActivity(Intent(applicationContext, Home::class.java))
                    finish()
                }
            } else {
                StaticMethods.alert(this,message)
            }
        })
    }

    override fun onApiFailed(message: String) {
        progressBar.hide()
        toast(message)
    }

}