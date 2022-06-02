package com.farmershop.data.`interface`

import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import androidx.lifecycle.LiveData
import com.farmershop.data.network.PostPojo
import kotlinx.android.synthetic.main.include_toolbar_product.*

interface BaseInterface {
    fun onApiStart()
    fun onApiSuccess(apiResponse: LiveData<ApiPojo>, responseFlag:String?)
    fun onApiSuccess1(apiResponse: LiveData<ApiPojoArray>, responseFlag:String?)
    fun onApiSuccess2(apiResponse: LiveData<ArrayList<PostPojo>>, responseFlag:String?)
    fun onApiFailed(message:String)
    fun onDrawerItemClick(int:Int)
    fun onAdapterItemClick(args:Array<String>)
}