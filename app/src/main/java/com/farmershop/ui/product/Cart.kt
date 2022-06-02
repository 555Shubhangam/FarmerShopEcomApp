package com.farmershop.ui.product

import com.farmershop.R
import com.farmershop.appSDK.BaseActivityUser
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.appSDK.hide
import com.farmershop.appSDK.show
import com.farmershop.appSDK.toast
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import com.farmershop.ui.product.adapter.CartItemAdapter
import com.farmershop.ui.product.adapter.CategoryItemAdapter
import kotlinx.android.synthetic.main.category_items.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.include_toolbar.*

class Cart : BaseActivityUser() {

    lateinit var viewModel:ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
        init()
    }

    private fun init(){
        toolbar_title.text="Cart"
        toolbar_back.setOnClickListener {
            onBackPressed()
        }
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel?.baseInterface = this
        recycler_view_items.layoutManager=
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        viewModel.cartItemList()
    }

    //-----------API Response------------
    override fun onApiStart() {
        progressBar.show()
    }

    override fun onApiSuccess1(apiResponse: LiveData<ApiPojoArray>, responseFlag: String?) {
        apiResponse.observe(this, Observer {
            progressBar.hide()
            val response = apiResponse.value!!
            Log.e(TAG, response.toString())
            val message = response.message.toString()
            if (response.status.toString() == "1") {
                if (responseFlag == "categoryItemList") {
                    val data = response.data
                    val adapter= CartItemAdapter(applicationContext,data!!,this)
                    recycler_view_items.adapter=adapter
                    adapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onApiFailed(message: String) {
        progressBar.hide()
        Log.e("onApiFailed", message)
        toast(message)
    }

    fun updateCart(){
        viewModel.cartItemList()
    }

}