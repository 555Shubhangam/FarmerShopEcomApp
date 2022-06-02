package com.farmershop.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser

class Location : BaseActivityUser() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location)
    }
}