package com.farmershop.ui.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.ui.base.BaseActivity
import com.farmershop.databinding.ItemAddtocartBinding
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.include_toolbar.*

class AddToCartActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_cart)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.addCart)

        tvProductAdd.setOnClickListener {
            intent = Intent(this, OrderSummaryActivity::class.java)
            startActivity(intent)
        }
        recycler_addToCart.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        setAdapterProduct(recycler_addToCart)
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
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ItemAddtocartBinding>(
            minAgeArray,
            R.layout.item_addtocart, object :
                RecyclerCallback<ItemAddtocartBinding, String> {
                override fun bindData(
                    binder: ItemAddtocartBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                        /* txtName.text = model

                         llItemTop.setOnClickListener{
                             updateTextViewDataForMinAge(txtName.getText().toString())
                             bottomSheetFilterDialog?.dismiss()
                         }*/
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }}