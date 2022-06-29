package com.farmershop.mvvm_data.ui.Activity

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.databinding.ProductDescriptionBinding
import com.farmershop.mvvm_data.ui.adapter.RecyclerCallback
import com.farmershop.mvvm_data.ui.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.include_toolbar.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.setText("Search Product")
        recyclerProduct.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        setAdapterProduct(recyclerProduct)
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
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ProductDescriptionBinding>(
            minAgeArray,
            R.layout.product_description, object :
                RecyclerCallback<ProductDescriptionBinding, String> {
                override fun bindData(
                    binder: ProductDescriptionBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                        tvProductAdd.setOnClickListener{
                            val intent =Intent(this@SearchActivity,AddToCartActivity::class.java)
                            startActivity(intent)
                         }
                        relativeMain.setOnClickListener{
                            val intent =Intent(this@SearchActivity,ProductDescriptionActivity::class.java)
                            startActivity(intent)
                        }

                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }
}