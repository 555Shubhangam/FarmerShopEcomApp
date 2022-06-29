package com.farmershop.ui.product

import com.farmershop.data.`interface`.BaseInterface
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.farmershop.appSDK.AppSession
import com.farmershop.data.repository.ProductRepository

class ProductViewModel (application: Application) : AndroidViewModel(application) {
    lateinit var baseInterface: BaseInterface
    var mContext=application.applicationContext

    fun postList(){
        baseInterface.onApiStart()
        val response= ProductRepository().postList()
        baseInterface.onApiSuccess2(response,"postList")
    }

    fun categoryList(){
        baseInterface.onApiStart()
        val response= ProductRepository().categoryList()
        baseInterface.onApiSuccess1(response,"categoryList")
    }

    fun cartItemList(){
        baseInterface.onApiStart()
        val response= ProductRepository().cartItemList(AppSession.getInstance(mContext).getUserId().toString())
        baseInterface.onApiSuccess1(response,"categoryItemList")
    }

    fun categoryItemList(category_id:String){
        baseInterface.onApiStart()
        val response= ProductRepository().categoryItemList(category_id,
            AppSession.getInstance(mContext).getUserId().toString())
        baseInterface.onApiSuccess1(response,"categoryItemList")
    }

    fun updateCart(product_id:String,qty:String){
        baseInterface.onApiStart()
        val response= ProductRepository().updateCart(product_id,qty,
            AppSession.getInstance(mContext).getUserId().toString())
        baseInterface.onApiSuccess(response,"updateCart")
    }

    fun getHomeCounter(){
        baseInterface.onApiStart()
        val response= ProductRepository().getHomeCounter(AppSession.getInstance(mContext).getUserId().toString())
        baseInterface.onApiSuccess(response,"getHomeCounter")
    }

}