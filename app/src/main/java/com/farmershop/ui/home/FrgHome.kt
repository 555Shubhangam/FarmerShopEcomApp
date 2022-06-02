package com.farmershop.ui.home

import android.content.Context
import android.content.Intent
import com.farmershop.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.BuildConfig
import com.farmershop.appSDK.*
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import com.farmershop.ui.auth.AuthViewModel
import com.farmershop.ui.auth.OTP
import com.farmershop.ui.product.ProductViewModel
import com.farmershop.ui.product.adapter.HomeCategoryAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_progress_bar.*
import kotlinx.android.synthetic.main.tab_login.*

class FrgHome : BaseFragment() {

    lateinit var mContext: Context
    lateinit var viewModel:ProductViewModel
    companion object {
        @JvmStatic
        fun newInstance():FrgHome{
            return  FrgHome()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        TAG = "FrgHome"
        mContext = activity?.applicationContext!!
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel?.baseInterface = this
        recycler_view_category.layoutManager=GridLayoutManager(mContext,2)
        recycler_view_popular.layoutManager=LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false)
        viewModel.categoryList()
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
                if (responseFlag == "categoryList") {
                    val data = response.data
                    val adapter=HomeCategoryAdapter(mContext,data!!)
                    recycler_view_category.adapter=adapter
                    adapter.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onApiFailed(message: String) {
        progressBar.hide()
        Log.e("onApiFailed", message)
        mContext.toast(message)
    }

}