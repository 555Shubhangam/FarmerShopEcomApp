package com.farmershop.ui.search

import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser
import android.os.Bundle

class Search : BaseActivityUser() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
    }
}