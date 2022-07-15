package com.farmershop.mvvm_data.ui.Activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.appSDK.BaseActivity
import com.farmershop.databinding.ItemFaqBinding
import com.farmershop.databinding.ItemFaqQuesAnsBinding
import com.farmershop.mvvm_data.ui.adapter.RecyclerCallback
import com.farmershop.mvvm_data.ui.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.activity_faqactivity.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.item_faq_ques_ans.*

class FAQActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqactivity)
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.faq)

        rvFaq.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setAdapterProduct(rvFaq)
    }
    fun setAdapterProduct(recyclerView: RecyclerView) {
        val minAgeArray = ArrayList<String>()
        minAgeArray.add("15")
        minAgeArray.add("16")
        minAgeArray.add("17")
        minAgeArray.add("18")
        minAgeArray.add("19")
        minAgeArray.add("20")
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ItemFaqBinding>(
            minAgeArray,
            R.layout.item_faq, object :
                RecyclerCallback<ItemFaqBinding, String> {
                override fun bindData(
                    binder: ItemFaqBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {
                        rvFaqQuesAns.layoutManager = LinearLayoutManager(this@FAQActivity, RecyclerView.VERTICAL, false)
                        setAdapterProduct1(rvFaqQuesAns)
                        //  txtName.text = model
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress
    }

    fun setAdapterProduct1(recyclerView: RecyclerView) {
        val minAgeArray = ArrayList<String>()
        var pos = 0
        var isOpenDropDown = false
        minAgeArray.add("15")
        minAgeArray.add("16")
        minAgeArray.add("17")
        minAgeArray.add("18")
        minAgeArray.add("19")
        minAgeArray.add("20")
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ItemFaqQuesAnsBinding>(
            minAgeArray,
            R.layout.item_faq_ques_ans, object :
                RecyclerCallback<ItemFaqQuesAnsBinding, String> {
                override fun bindData(
                    binder: ItemFaqQuesAnsBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {
                        imgDropDown.setOnClickListener {
                            if(isOpenDropDown){
                                isOpenDropDown = false
                                imgDropDown.setImageResource(R.drawable.ic_drop_down)
                                tvAns.visibility = View.GONE
                            }else{
                                isOpenDropDown = true
                                imgDropDown.setImageResource(R.drawable.ic_drop_up)
                                tvAns.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress
    }
}