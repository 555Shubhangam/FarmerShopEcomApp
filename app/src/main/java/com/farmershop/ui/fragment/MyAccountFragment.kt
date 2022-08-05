package com.farmershop.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.farmershop.R
import com.farmershop.ui.activity.ChangeEmailMobileActivity
import com.farmershop.ui.base.BaseFragment
import com.farmershop.ui.activity.MyAddressActivity
import com.farmershop.ui.activity.MyOrderActivity
import com.farmershop.ui.activity.Profile
import com.farmershop.ui.activity.setting.ChangePasswordActivity
import com.farmershop.utils.AppSession
import kotlinx.android.synthetic.main.fragment_my_account.*

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
        tvName.text = AppSession.getName()
        tvEmailId.text = AppSession.getEmail()
        tvPhoneNo.text = AppSession.getMobile()
        Glide.with(this)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
            )
            .load(AppSession.getPhoto())
            .into(userProfile)
        tvDeliveryAddress.setOnClickListener {
            var intent =Intent(requireContext(), MyAddressActivity::class.java)
            startActivity(intent)
        }
        tvResetPassword.setOnClickListener {
            var intent =Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }


        tvOrderHistory.setOnClickListener {
            var intent =Intent(requireContext(), MyOrderActivity::class.java)
            startActivity(intent)
        }
        tvWishlist.setOnClickListener {
            addFragment(WishlistFragment.newInstance())

        }
        tvEditProfile.setOnClickListener {
            var intent =Intent(requireContext(), Profile::class.java)
            startActivity(intent)
        }
        tvChangeMobileEmail.setOnClickListener {
            var intent =Intent(requireContext(), ChangeEmailMobileActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

}