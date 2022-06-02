package com.farmershop.ui.auth

import com.farmershop.R
import com.farmershop.appSDK.BaseActivity
import com.farmershop.databinding.LoginBinding
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.login.*


class Login : BaseActivity() {

    var viewModel: AuthViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: LoginBinding = DataBindingUtil.setContentView(this, R.layout.login)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel!!.baseInterface = this
        binding.viewModel = viewModel
        init()
    }

    private fun init(){
        app_version.text=applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0).versionName

        val pagerAdapter = MyPagerAdapter(supportFragmentManager)
        pagerAdapter?.addFragment(TabLogin(this), "Login")
        pagerAdapter?.addFragment(TabRegister(this), "Register")
        viewpager.adapter = pagerAdapter
        tab_layout.setupWithViewPager(viewpager)
        if(intent.hasExtra("selected_tab")){
            viewpager.currentItem = intent.getStringExtra("selected_tab").toInt()
        }
    }

    class MyPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        private val mFragmentList: ArrayList<Fragment> = ArrayList()
        private val mFragmentTitleList: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList.get(position)
        }
    }


    override fun onBackPressed() {
        //super.onBackPressed()

    }
}