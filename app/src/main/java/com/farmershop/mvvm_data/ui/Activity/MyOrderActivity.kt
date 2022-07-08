package com.farmershop.mvvm_data.ui.Activity

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.databinding.ItemAddtocartBinding
import com.farmershop.databinding.ItemMyOrderBinding
import com.farmershop.mvvm_data.ui.adapter.RecyclerCallback
import com.farmershop.mvvm_data.ui.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.activity_my_order.*
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MyOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.myOrders)

        toolbar_back.setOnClickListener {
            finish()
        }
        rvMyOrder.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setAdapterProduct(rvMyOrder)
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
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ItemMyOrderBinding>(
            minAgeArray,
            R.layout.item_my_order, object :
                RecyclerCallback<ItemMyOrderBinding, String> {
                override fun bindData(
                    binder: ItemMyOrderBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                       //  txtName.text = model

                         tvFirstBtn.setOnClickListener{
                             var intent =Intent(this@MyOrderActivity,MyOrderDetailsActivity::class.java)
                             startActivity(intent)
                         }
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }
}