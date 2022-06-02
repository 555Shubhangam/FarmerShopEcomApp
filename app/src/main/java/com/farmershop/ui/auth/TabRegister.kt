package com.farmershop.ui.auth


import com.farmershop.R
import com.farmershop.appSDK.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.tab_register.*

class TabRegister(val parent:Login): BaseFragment() {
    lateinit var mContext: Context
    var viewModel: AuthViewModel?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.tab_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        TAG = "TabRegister"
        mContext = activity?.applicationContext!!
        viewModel= ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel?.baseInterface=this
        btnSubmit.setOnClickListener {
            if(Validation.isValidEmailOrMobile(username.text.toString())){
                val intent=Intent(mContext,OTP::class.java)
                intent.putExtra("purpose","register")
                intent.putExtra("username",username.text.toString())
                startActivity(intent)
            }else{
                StaticMethods.showAlert(activity!!,"Please enter valid email-id/mobile number")
            }
        }
    }

    //-----------API Response------------
    override fun onApiStart() {
        progressBar.show()
    }

    override fun onApiFailed(message: String) {
        progressBar.hide()
        Log.e("onApiFailed",message)
        mContext.toast(message)
    }
}