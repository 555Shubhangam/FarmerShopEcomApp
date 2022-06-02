package com.farmershop.ui.store

import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser
import android.os.Bundle

class Store : BaseActivityUser() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.store)
    }
}