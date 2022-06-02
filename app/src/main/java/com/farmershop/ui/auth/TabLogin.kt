package com.farmershop.ui.auth


import com.exfactor.appsdk.AppSession
import com.farmershop.BuildConfig
import com.farmershop.R
import com.farmershop.appSDK.*
import com.farmershop.data.network.ApiPojo
import com.farmershop.ui.home.Home
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.tab_login.*

class TabLogin(val parent: Login) : BaseFragment() {
    lateinit var mContext: Context
    var viewModel: AuthViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.tab_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if(BuildConfig.DEBUG){
            username.setText("7828796979")
            password.setText("1234")
        }
        TAG = "TabLogin"
        mContext = activity?.applicationContext!!
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        ckbOTPLogin.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                password_layout.visibility=View.GONE
            }else{
                password_layout.visibility=View.VISIBLE
            }
        }
        viewModel?.baseInterface = this
        btnSubmit.setOnClickListener {
            if(ckbOTPLogin.isChecked){
                viewModel!!.checkUsername(username.text.toString())
            }else{
                viewModel!!.login(username.text.toString(), password.text.toString())
            }
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
            Log.e(TAG, response.toString())
            val message = response.message.toString()
            if (response.status.toString() == "1") {
                if (responseFlag == "Login") {
                    val data = response.data
                    StaticMethods.setUserSession(mContext,data!!)
                    startActivity(Intent(mContext, Home::class.java))
                    parent.finish()
                } else if (responseFlag == "checkUsername") {
                    val data = response.data
                    val intent=Intent(mContext, OTP::class.java)
                    intent.putExtra("purpose","login")
                    intent.putExtra("username",username.text.toString())
                    intent.putExtra("customer_id",data?.customer_id)
                    startActivity(intent)
                    parent.finish()
                }
            } else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onApiFailed(message: String) {
        progressBar.hide()
        Log.e("onApiFailed", message)
        mContext.toast(message)
    }

}