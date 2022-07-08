package com.farmershop.mvvm_data.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.appSDK.BaseActivity
import com.farmershop.databinding.ItemMyOrderBinding
import com.farmershop.databinding.ItemOrderSummaryBinding
import com.farmershop.mvvm_data.ui.adapter.RecyclerCallback
import com.farmershop.mvvm_data.ui.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.activity_my_order.*
import kotlinx.android.synthetic.main.activity_order_summary.*
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.include_toolbar.*

class OrderSummaryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.orderSummary)

        tvAddEditAddress.setOnClickListener {
            var intent =Intent(this@OrderSummaryActivity,EditAddressActivity::class.java)
            startActivity(intent)
        }
        rvOrderSummary.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setAdapterProduct(rvOrderSummary)
    }
    fun setAdapterProduct(recyclerView: RecyclerView) {
        val minAgeArray = ArrayList<String>()
        minAgeArray.add("15")
        minAgeArray.add("16")
        minAgeArray.add("17")
        minAgeArray.add("18")
        minAgeArray.add("19")
        minAgeArray.add("20")
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ItemOrderSummaryBinding>(
            minAgeArray,
            R.layout.item_order_summary, object :
                RecyclerCallback<ItemOrderSummaryBinding, String> {
                override fun bindData(
                    binder: ItemOrderSummaryBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        //  txtName.text = model
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress
    }
}