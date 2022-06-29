package com.farmershop.mvvm_data.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.databinding.ItemAddtocartBinding
import com.farmershop.databinding.ProductDescriptionBinding
import com.farmershop.mvvm_data.ui.adapter.RecyclerCallback
import com.farmershop.mvvm_data.ui.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.fragment_add_to_cart.*
import kotlinx.android.synthetic.main.fragment_product.*

class AddToCartFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(): AddToCartFragment {
            return  AddToCartFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_to_cart, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {

        recycler_addToCart.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        setAdapterProduct(recycler_addToCart)
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
        val rvAdapProgress = RecyclerViewGenricAdapter<String, ItemAddtocartBinding>(
            minAgeArray,
            R.layout.item_addtocart, object :
                RecyclerCallback<ItemAddtocartBinding, String> {
                override fun bindData(
                    binder: ItemAddtocartBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        tvProductPrice.setPaintFlags(tvProductPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                        /* txtName.text = model

                         llItemTop.setOnClickListener{
                             updateTextViewDataForMinAge(txtName.getText().toString())
                             bottomSheetFilterDialog?.dismiss()
                         }*/
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }
}