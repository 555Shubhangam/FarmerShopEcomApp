package com.farmershop.appSDK

import android.content.Context
import com.farmershop.data.`interface`.BaseInterface
import com.farmershop.data.network.ApiPojo
import com.farmershop.data.network.ApiPojoArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import com.farmershop.R
import com.farmershop.data.network.PostPojo

open class BaseFragment: Fragment(),BaseInterface {
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

    fun addFragment(fragment: Fragment) {

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}