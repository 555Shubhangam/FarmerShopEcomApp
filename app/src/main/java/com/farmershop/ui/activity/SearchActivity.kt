package com.farmershop.ui.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.databinding.ProductDescriptionBinding
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.include_toolbar.*

import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.farmershop.data.adapter.PopupDropDownListAdapter
import com.farmershop.data.adapter.VariantListAdapter
import com.farmershop.data.model.PopupDropDownListModel
import com.farmershop.data.model.response.AVariant
import com.farmershop.data.model.response.SearchData
import com.farmershop.data.model.response.SearchResponse
import com.farmershop.data.viewModel.SearchProductViewModel
import com.farmershop.databinding.ActivitySearchBinding
import com.farmershop.databinding.CustomDropDownItemLayoutBinding
import com.farmershop.databinding.ItemDropListBinding
import com.farmershop.utils.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_product.recyclerProduct
import kotlinx.android.synthetic.main.login.*
import androidx.core.widget.NestedScrollView
import com.farmershop.data.model.request.AddCartRequest
import com.farmershop.data.model.request.RegisterRequest
import com.farmershop.data.viewModel.AddCartViewModel
import com.farmershop.data.viewModel.SignupViewModel
import com.farmershop.ui.activity.auth.Login


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModal: SearchProductViewModel
    var productList: ArrayList<SearchData> = ArrayList()
    var kgList = ArrayList<SearchResponse>()
    private var rvAdapSearch: RecyclerViewGenricAdapter<SearchData, ProductDescriptionBinding>? = null
    var pageNo = 0
    var limit:Int = 10
    var pageSize:String = "10"
    private lateinit var viewModalAddCart: AddCartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this@SearchActivity,R.layout.activity_search)
        viewModal=ViewModelProvider(this@SearchActivity).get(SearchProductViewModel::class.java)
        viewModalAddCart=ViewModelProvider(this@SearchActivity).get(AddCartViewModel::class.java)
        init()
        setObserver()


        init()
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.setText("Search Product")

    }
    private  fun init() {
        searchProduct("","")
        txt_searchProduct.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
             Log.d("dfdsfdsfg1",s.toString())

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
                Log.d("dfdsfdsfg2",s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length>3){
                    searchProduct(s.toString(),"0")
                }
                else{
                   // searchProduct("")

                }


            }
        })


        nestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                pageNo++
                Log.d("dsadfcdsdsda",pageNo.toString())
                ProgressDialog.showProgressBar(this)
                searchProduct(txt_searchProduct.text.toString().trim(),pageNo.toString())
            }
        })
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
        minAgeArray.add("25")*/
        rvAdapSearch = RecyclerViewGenricAdapter<SearchData, ProductDescriptionBinding>(
            productList,
            R.layout.product_description, object :
                RecyclerCallback<ProductDescriptionBinding, SearchData> {
                override fun bindData(
                    binder: ProductDescriptionBinding,
                    model: SearchData,
                    position: Int,
                    itemView: View
                ) {
                    var varientID = 0
                    binder.apply {
                        Log.d("namweweddcd",productList.toString())
                        tvVegitableName.text=model.name
                        tvProductDiscountPrice.text=model.price
                        tvProductPrice.text=model.old_price
                        tvOffer.text=model.discount_percent

                        Glide.with(this@SearchActivity)
                            .load(model.img_1200_1200)
                            .into(imgProduct);


                           if (/*model.aVariant[position].status == "OUT-STOCK" ||*/ model.status == "OUT-STOCK"){
                               lnrAdd.visibility=View.GONE
                               layoutAddProduct.visibility=View.GONE
                               lnrOutOfStock.visibility=View.VISIBLE
                           }
                           else{
                               lnrAdd.visibility=View.VISIBLE
                               layoutAddProduct.visibility=View.GONE
                               lnrOutOfStock.visibility=View.GONE
                               }

                          for (varrientName in model.aVariant){
                              if (model.product_variant_id==varrientName.variant_id){
                                  tvquantity.text=varrientName.variant_name
                                  Log.d("ttddsddd",model.product_variant_id.toString())
                                  Log.d("cdscdsacdsdd",varrientName.variant_id.toString())
                              }
                          }

                        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                        setObserverAddCart(lnrAdd,layoutAddProduct,lnrOutOfStock)
                        tvProductAdd.setOnClickListener{
                            for (varrientName in model.aVariant){
                                if (tvquantity.text.toString()==varrientName.variant_name){
                                    varientID=varrientName.variant_id
                                    Log.d("ttddsddd",model.product_variant_id.toString())
                                }
                            }
                            addCart(varientID,tvCountNo.text.toString().toInt())
                            lnrAdd.visibility=View.GONE
                            layoutAddProduct.visibility=View.VISIBLE
                            lnrOutOfStock.visibility=View.GONE

                         }
                        relativeMain.setOnClickListener{
                            val intent =Intent(this@SearchActivity, ProductDescriptionActivity::class.java)
                            startActivity(intent)
                        }

                        var quantity =1
                       /* if (qty!=0){
                            quantity =qty
                            lnrAdd.visibility=View.GONE
                            layoutAddProduct.visibility=View.VISIBLE
                            lnrOutOfStock.visibility=View.GONE
                            tvCountNo.text=quantity.toString()

                        }*/

                        imgIncreaseQuantity.setOnClickListener{
                            quantity++
                            //quantity= ++quantity
                            tvCountNo.text=quantity.toString()

                            Log.d("sdsdfdfcd",quantity.toString())
                            addCart(varientID,tvCountNo.text.toString().toInt())

                        }
                        imgDecreaseQuantity.setOnClickListener{
                            quantity--
                            //quantity=--quantity
                            tvCountNo.text=quantity.toString()
                            if (quantity==0){
                               quantity =1
                                tvCountNo.text=quantity.toString()
                                lnrAdd.visibility=View.VISIBLE
                                layoutAddProduct.visibility=View.GONE
                                lnrOutOfStock.visibility=View.GONE

                            }
                            addCart(varientID,tvCountNo.text.toString().toInt())


                        }





                        tvquantity.setOnClickListener {
                            showPopUpWindow(this@SearchActivity, model.aVariant,tvquantity,lnrAdd,layoutAddProduct,lnrOutOfStock)
                        }

                    }
                }
            })
        recyclerView.adapter = rvAdapSearch

    }
    private fun searchProduct(searchKey:String,pageNo:String) {

        viewModal.searchProduct(searchKey,"","",pageSize,pageNo,AppSession.getUserId()!!.toInt())
    }

    private fun setObserver() {
        viewModal.searchProductResponse.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    productList=response.data?.data!!
                    recyclerProduct.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
                    setAdapterProduct(recyclerProduct)
                    Log.d("sfdfsdfdfd",productList.toString())
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

    private fun addCart(product_variant_id:Int,quantity:Int) {
        val request = AddCartRequest(AppSession.getUserId()!!.toInt(),product_variant_id,quantity)
        Log.d("dhdfhdddnd",request.toString())
        viewModalAddCart.addCart(request)
    }

    private fun setObserverAddCart(lnrAdd:LinearLayout,layoutAddProduct:LinearLayout,lnrOutOfStock:LinearLayout) {
        viewModalAddCart.addCartResponse.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()

                    /*lnrAdd.visibility=View.GONE
                    layoutAddProduct.visibility=View.VISIBLE
                    lnrOutOfStock.visibility=View.GONE*/
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


    fun showPopUpWindow(
        context: Context,
        arrayList: ArrayList<AVariant>,tvquantity:TextView,lnrAdd:LinearLayout,layoutAddProduct:LinearLayout,lnrOutOfStock:LinearLayout) { // inflate the layout of the popup window
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_drop_down_item_layout, null, false)
        (context as Activity).window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setContentView(view)
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        // window.setBackgroundDrawableResource(R.color.colorTransparent)
        window.setGravity(Gravity.CENTER)


        val itemDropdownBinding: CustomDropDownItemLayoutBinding = DataBindingUtil.bind(view)!!
        setupRecyclerPopupView(arrayList,itemDropdownBinding,dialog, context,tvquantity,lnrAdd,layoutAddProduct,lnrOutOfStock)
        dialog.show()
        itemDropdownBinding.clsSearch.visibility = View.GONE
    }
    private fun setupRecyclerPopupView(arrayList: ArrayList<AVariant>, itemDropdownBinding:CustomDropDownItemLayoutBinding, dialog: Dialog,
                                       context: Activity,tvquantity:TextView
                                       ,lnrAdd:LinearLayout,layoutAddProduct:LinearLayout,lnrOutOfStock:LinearLayout) {
        itemDropdownBinding.rvItems.apply {
            val mPopupAdapter =
                VariantListAdapter(
                    context, object : VariantListAdapter.onClickListener {
                        override fun onSelectItem(
                            position: Int,
                            arrayList: ArrayList<AVariant>
                        ) {
                            Utility.hideKeyboard(context)
                            dialog.dismiss()
                        //after click set logic...
                         tvquantity.text = arrayList[position].variant_name
                            if (arrayList[position].status == "OUT-STOCK"){
                                lnrAdd.visibility=View.GONE
                                layoutAddProduct.visibility=View.GONE
                                lnrOutOfStock.visibility=View.VISIBLE
                            }
                            else{
                                lnrAdd.visibility=View.VISIBLE
                                layoutAddProduct.visibility=View.GONE
                                lnrOutOfStock.visibility=View.GONE
                            }
                        }
                    })
            mPopupAdapter.setDataList(arrayList)
            adapter = mPopupAdapter
        }
        itemDropdownBinding.imgCancel.setOnClickListener {
            Utility.hideKeyboard(context)
            dialog.dismiss()
        }
    }
}