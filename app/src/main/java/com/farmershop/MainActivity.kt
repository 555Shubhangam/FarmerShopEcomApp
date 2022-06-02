package com.farmershop

import com.exfactor.appsdk.AppSession
import com.farmershop.ui.auth.Login
import com.farmershop.ui.home.Home
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val handler = Handler()
        handler.postDelayed({
            var intent = Intent(this, Login::class.java)
            if (AppSession.getInstance(applicationContext).isLoggedIn()) {
                intent = Intent(this, Home::class.java)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }, 2000)
    }
}