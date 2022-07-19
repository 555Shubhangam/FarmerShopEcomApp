package com.farmershop.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmershop.R
import kotlinx.android.synthetic.main.activity_product_description.*
import kotlinx.android.synthetic.main.include_toolbar.*

class ProductDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_description)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.setText("Product Details")
        AddTocart.setOnClickListener{
            val intent = Intent(this, AddToCartActivity::class.java)
            startActivity(intent)

        }
        BuyNow.setOnClickListener{
            val intent = Intent(this, EditAddressActivity::class.java)
            startActivity(intent)

        }

    }
}