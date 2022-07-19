package com.farmershop.ui.fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.ui.base.BaseFragment
import com.farmershop.databinding.ProductDescriptionBinding
import com.farmershop.ui.activity.AddToCartActivity
import com.farmershop.ui.activity.ProductDescriptionActivity
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : BaseFragment() {
    companion object {
        @JvmStatic
        fun newInstance(): ProductFragment {
            return  ProductFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }
    private fun initView() {

        recyclerProduct.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        setAdapterProduct(recyclerProduct)
        }
    fun setAdapterProduct(recyclerView: RecyclerView) {
        val minAgeArray = ArrayList<String>()
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
        minAgeArray.add("25")
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ProductDescriptionBinding>(
            minAgeArray,
            R.layout.product_description, object :
                RecyclerCallback<ProductDescriptionBinding, String> {
                override fun bindData(
                    binder: ProductDescriptionBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                        relativeMain.setOnClickListener{
                             val intent =Intent(requireContext(), ProductDescriptionActivity::class.java)
                                startActivity(intent)

                        }
                        tvProductAdd.setOnClickListener{
                         val intent =Intent(requireContext(), AddToCartActivity::class.java)
                            startActivity(intent)

                         }
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }


}