package com.farmershop.mvvm_data.ui.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmershop.R
import kotlinx.android.synthetic.main.include_toolbar.*

class EditAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.setText("Add Address")
    }
}