package com.farmershop.ui.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmershop.R
import com.farmershop.ui.base.BaseActivity
import com.farmershop.databinding.ItemAddtocartBinding
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import com.farmershop.data.model.response.CartListData
import com.farmershop.data.model.response.SearchData
import com.farmershop.data.model.response.SearchResponse
import com.farmershop.data.viewModel.CartListViewModel
import com.farmershop.data.viewModel.SearchProductViewModel
import com.farmershop.databinding.ActivityAddToCartBinding
import com.farmershop.databinding.ActivitySearchBinding
import com.farmershop.databinding.ProductDescriptionBinding
import com.farmershop.utils.AppSession
import com.farmershop.utils.ProgressDialog
import com.farmershop.utils.Resource
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.include_toolbar.*

class AddToCartActivity : BaseActivity() {
    private lateinit var binding: ActivityAddToCartBinding
    private lateinit var viewModal: CartListViewModel
    var productList: ArrayList<CartListData> = ArrayList()
    private var rvAdapCartList: RecyclerViewGenricAdapter<CartListData, ItemAddtocartBinding>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this@AddToCartActivity,R.layout.activity_add_to_cart)
        viewModal= ViewModelProvider(this@AddToCartActivity).get(CartListViewModel::class.java)
        init()
        setObserver()

    }

    private  fun init() {
        cartList()
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.addCart)

        tvProductAdd.setOnClickListener {
            intent = Intent(this, OrderSummaryActivity::class.java)
            startActivity(intent)
        }


    }

    fun setAdapterProduct(recyclerView: RecyclerView) {
      /*  val minAgeArray = ArrayList<String>()
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
        val rvAdapProgress =*/
        rvAdapCartList=RecyclerViewGenricAdapter<CartListData, ItemAddtocartBinding>(
            productList,
            R.layout.item_addtocart, object :
                RecyclerCallback<ItemAddtocartBinding, CartListData> {
                override fun bindData(
                    binder: ItemAddtocartBinding,
                    model: CartListData,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                        tvVegitableName.text=model.name
                        tvquantityCartList.text=model.qty.toString()
                        /*tvProductDiscountPrice.text=model.price
                        tvProductPrice.text=model.old_price*/
                        /*tvOffer.text=model.discount_percent*/

                        Glide.with(this@AddToCartActivity)
                            .load(model.img_1200_1200)
                            .into(productImg);

                    }
                }
            })
        recyclerView.adapter = rvAdapCartList

    }


    private fun cartList() {

        viewModal.cartList(AppSession.getUserId()!!.toInt())
    }

    private fun setObserver() {
        viewModal.cartListResponse.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    productList=response.data?.data!!

                    recycler_addToCart.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
                    setAdapterProduct(recycler_addToCart)


                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_LONG).show()

                    Log.wtf("sdsfdsfvdfd", "ressponse message --- : " + response.message.toString())
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {

                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}