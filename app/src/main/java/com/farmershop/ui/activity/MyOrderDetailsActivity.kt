package com.farmershop.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.databinding.ItemMyOrderDetailsBinding
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.activity_my_order_details.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MyOrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order_details)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.myOrdersDetails)

        toolbar_back.setOnClickListener {
            finish()
        }
        rvMyOrderDetails.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setAdapterProduct(rvMyOrderDetails)
    }
    fun setAdapterProduct(recyclerView: RecyclerView) {
        val minAgeArray = ArrayList<String>()
        minAgeArray.add("15")
        minAgeArray.add("16")
        minAgeArray.add("17")
        minAgeArray.add("18")
        minAgeArray.add("19")
        minAgeArray.add("20")
        minAgeArray.add("21")
        minAgeArray.add("22")
        minAgeArray.add("23")
        minAgeArray.add("24")
        minAgeArray.add("25")
        val rvAdapProgress = RecyclerViewGenricAdapter(
            minAgeArray,
            R.layout.item_my_order_details, object :
                RecyclerCallback<ItemMyOrderDetailsBinding, String> {
                override fun bindData(
                    binder: ItemMyOrderDetailsBinding,
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