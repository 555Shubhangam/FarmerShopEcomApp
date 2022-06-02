package com.farmershop.ui.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exfactor.appsdk.AppSession
import com.farmershop.R
import com.farmershop.appSDK.*
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import com.farmershop.ui.product.adapter.CategoryItemAdapter
import com.farmershop.ui.product.adapter.HomeCategoryAdapter
import kotlinx.android.synthetic.main.category_items.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.include_toolbar_product.*

class CategoryItems : BaseActivityUser() {

    private var category_id="0"
    lateinit var viewModel:ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_items)
        init()
    }

    private fun init() {
        if (intent.hasExtra("category")) {
            toolbar_title.text = intent.getStringExtra("category")
            category_id= intent.getStringExtra("category_id")
        }
        toolbar_back.setOnClickListener {
            onBackPressed()
        }
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel?.baseInterface = this
        recycler_view_items.layoutManager=LinearLayoutManager(applicationContext, RecyclerView.VERTICAL,false)
        viewModel.categoryItemList(category_id)
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
                    cart_counter.text=message // item count in cart
                    val adapter= CategoryItemAdapter(applicationContext,data!!,this)
                    recycler_view_items.adapter=adapter
                    adapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onApiSuccess(apiResponse: LiveData<ApiPojo>, responseFlag: String?) {
        apiResponse.observe(this, Observer {
            progressBar.hide()
            val response = apiResponse.value!!
            Log.e(TAG, response.toString())
            val message = response.message.toString()
            if (response.status.toString() == "1") {
                if (responseFlag == "updateCart") {
                    val data = response.data
                    if(data!!.item_count!=null && data!!.item_count.toString().toInt()>0){
                        cartLayout.visibility=View.VISIBLE
                        cart_counter.text=data!!.item_count.toString()
                    }else{
                        cartLayout.visibility=View.GONE
                    }
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

    fun updateCartCount(product_id:String,qty:String){
        viewModel.updateCart(product_id,qty)
    }

}