package com.farmershop.mvvm_data.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.appSDK.BaseFragment
import com.farmershop.mvvm_data.ui.Activity.MyAddressActivity
import com.farmershop.mvvm_data.ui.Activity.MyOrderActivity
import com.farmershop.ui.profile.Profile
import kotlinx.android.synthetic.main.fragment_my_address.*
import kotlinx.android.synthetic.main.fragment_product.*

class MyAccountFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): MyAccountFragment {
            return  MyAccountFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tvDeliveryAddress.setOnClickListener {
            var intent =Intent(requireContext(),MyAddressActivity::class.java)
            startActivity(intent)
        }
        tvOrderHistory.setOnClickListener {
            var intent =Intent(requireContext(),MyOrderActivity::class.java)
            startActivity(intent)
        }
        tvWishlist.setOnClickListener {
            addFragment(WishlistFragment.newInstance())

        }
        tvEditProfile.setOnClickListener {
            var intent =Intent(requireContext(),Profile::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_address, container, false)
    }

}