package com.farmershop.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser

class ProductDetails : BaseActivityUser() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)
    }
}