package com.farmershop.ui.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.farmershop.R
import com.farmershop.data.model.request.OTPRequest
import com.farmershop.data.viewModel.OTPViewModal
import com.farmershop.databinding.OtpBinding
import com.farmershop.ui.activity.Home
import com.farmershop.utils.*
import com.farmershop.ui.base.BaseActivity
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.otp.*

class OTP : BaseActivity() {
    lateinit var binding: OtpBinding
    private lateinit var viewModal: OTPViewModal
    var username = ""
    var otpVerifyFor = ""
   // var purpose="login"
    var purpose=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.otp)
        binding = DataBindingUtil.setContentView(this, R.layout.otp)
        viewModal = ViewModelProvider(this@OTP).get(OTPViewModal::class.java)
        init()
        setObserver()
    }

    private fun init() {
       /* auth = FirebaseAuth.getInstance()
        defineCallback()*/
       /* if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username")
            lblUsername.text = username
        }
        if(intent.hasExtra("purpose")){
            purpose = intent.getStringExtra("purpose")
        }*/
        username = intent.getStringExtra(Constants.USER_NAME)!!
        purpose = intent.getStringExtra(Constants.AUTH_TYPE)!!
        otpVerifyFor = intent.getStringExtra(Constants.OTP_VERIFY_FOR)!!

        lblUsername.text =username
        binding.btnVerify.setOnClickListener {
            Utility.hideKeyboard(this)
            validation()
        }


       // mobileOTP(username)

        /*if (Validation.isValidMobile(username)) {
            mobileOTP(username)
        }*/

       /* btnResend.setOnClickListener {
            resendMobileOTP(username, resendToken)
        }

        btnVerify.setOnClickListener {
            verifyMobileNumber(storedVerificationId, otp.text.toString())
        }*/

    }

    private fun validation() {
        if(Validation.isEmpty(binding.edtotp.text.toString())){
            StaticMethods.alert(this, getString(R.string.otp_required))
        }  else{
            Utility.hideKeyboard(this)
            otpVerify()
        }
    }
    private fun otpVerify() {
      val  otp = binding.edtotp.text.toString()
       // val request = OTPRequest(username,otp)
        Log.wtf("resspontyt ",otpVerifyFor)
        viewModal.otpVerification(username,otp,otpVerifyFor)
    }

    private fun setObserver() {
        viewModal.otpResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ",response.message.toString())

            when (response) {

                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()

                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()
                    when (purpose) {
                        "ForgotPassword" -> {
                            startActivity(Intent(this, ResetPasswordActivity::class.java)
                                .putExtra(Constants.USER_NAME,username))
                            finish()
                        }
                        "ChangeEmailMobile" -> {
                            finish()
                        }
                        else -> {
                            startActivity(Intent(this, Home::class.java))
                            finish()
                        }
                    }


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


/*    //-----------Firebase Mobile Verification Start--------------
    private lateinit var auth: FirebaseAuth
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private fun defineCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                verificationInProgress = false
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                verificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Invalid Mobile Number",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Snackbar.make(
                        findViewById(android.R.id.content), "Quota exceeded.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                toast("OTP Sent to your mobile number/email id")

            }
        }
    }

    private fun mobileOTP(phoneNumber: String) {
        val phoneNum = "+91$phoneNumber"
        Log.d("apicalssd","OTP SEND")
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNum,
            60,
            TimeUnit.SECONDS,
            this,
            callbacks)
        *//*val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")       // Phone number to verify
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        verificationInProgress = true*//*
    }

    private fun verifyMobileNumber(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendMobileOTP(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber")       // Phone number to verify
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    updateUI(user)
                } else {
                    Log.e("PhoneAuthCredential", task.exception.toString())
                    StaticMethods.alert(this,"Invalid OTP")
                }
            }
    }

    private fun updateUI(user: FirebaseUser? = auth.currentUser) {
        if (user != null) {
            StaticMethods.log("updateUI","OTP Verified Successfully")
            if(purpose=="login"){
               if(intent.hasExtra("customer_id")){
                   val customer_id=intent.getStringExtra("customer_id")
               }
            }else if(purpose=="register"){
                val intent=Intent(applicationContext, Register::class.java)
                intent.putExtra("username",username)
                startActivity(intent)
            }
        }else{
            StaticMethods.log("updateUI","OTP Not Verified")
        }
    }*/

}