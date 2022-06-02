package com.farmershop.ui.profile

import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser
import android.os.Bundle

class Profile : BaseActivityUser() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
    }
}