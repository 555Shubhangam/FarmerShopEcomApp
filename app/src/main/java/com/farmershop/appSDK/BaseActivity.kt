package com.farmershop.appSDK

import com.farmershop.data.`interface`.BaseInterface
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.farmershop.data.network.PostPojo
import kotlinx.android.synthetic.main.include_toolbar_product.*

open class BaseActivity: AppCompatActivity(),BaseInterface {
    var TAG=""
    override fun onApiStart() {
        TODO("Not yet implemented")
    }

    override fun onApiSuccess(apiResponse: LiveData<ApiPojo>, responseFlag: String?) {
        TODO("Not yet implemented")
    }



    override fun onApiSuccess1(apiResponse: LiveData<ApiPojoArray>, responseFlag: String?) {
        TODO("Not yet implemented")
    }

    override fun onApiSuccess2(apiResponse: LiveData<ArrayList<PostPojo>>, responseFlag: String?) {
        TODO("Not yet implemented")
    }

    override fun onApiFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDrawerItemClick(int: Int) {
        TODO("Not yet implemented")
    }

    override fun onAdapterItemClick(args: Array<String>) {
        TODO("Not yet implemented")
    }

}