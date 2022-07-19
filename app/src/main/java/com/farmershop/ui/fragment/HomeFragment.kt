package com.farmershop.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.ui.base.BaseFragment
import com.farmershop.databinding.*
import com.farmershop.ui.activity.AddToCartActivity
import com.farmershop.ui.activity.ProductDescriptionActivity
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import com.farmershop.data.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.content_lay_mainact.*

private const val TAG = "HomeFragment"
class HomeFragment : BaseFragment(){
    lateinit var mContext: Context
    lateinit var viewModel: ProductViewModel
    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return  HomeFragment()
        }
    }

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        layoutCategory.setOnClickListener {
            addFragment(CategoryFragment.newInstance())

        }
        layoutCategory1.setOnClickListener {
            addFragment(ProductFragment.newInstance())

        }
        layoutCategory2.setOnClickListener {
            addFragment(ProductFragment.newInstance())

        }
        /*TAG = "FrgHome"
        mContext = activity?.applicationContext!!
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel?.baseInterface = this
        recycler_view_category.layoutManager= GridLayoutManager(mContext,2)
        recycler_view_popular.layoutManager=
            LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false)
        viewModel.categoryList()*/
       // linearLayoutManagerProduct = GridLayoutManager(this, 2)
        recycler_category_mainAct.layoutManager = GridLayoutManager(requireContext(), 3)
        setAdapterProduct(recycler_category_mainAct)

        recycler_featured_mainAct.layoutManager = GridLayoutManager(requireContext(), 2)
        setAdapterOfferProduct(recycler_featured_mainAct)

        recycler_mostSelling_mainAct.layoutManager = GridLayoutManager(requireContext(), 2)
        setAdapterOfferProduct(recycler_mostSelling_mainAct)
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
        val rvAdapProgress = RecyclerViewGenricAdapter<String,CategoryRowHomeBinding>(
            minAgeArray,
            R.layout.category_row_home, object :
                RecyclerCallback<CategoryRowHomeBinding, String> {
                override fun bindData(
                    binder: CategoryRowHomeBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {

                        rlvCategory.setOnClickListener{
                            addFragment(ProductFragment.newInstance())

                        }
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }

    fun setAdapterOfferProduct(recyclerView: RecyclerView) {
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
        val rvAdapProgress = RecyclerViewGenricAdapter<String,ProductOffersBinding>(
            minAgeArray,
            R.layout.product_offers, object :
                RecyclerCallback<ProductOffersBinding, String> {
                override fun bindData(
                    binder: ProductOffersBinding,
                    model: String,
                    position: Int,
                    itemView: View
                ) {

                    binder.apply {
                        tvProductPrice.paintFlags = tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


                        relativeMain.setOnClickListener{
                            val intent = Intent(requireContext(), ProductDescriptionActivity::class.java)
                            startActivity(intent)

                        }
                        tvProductAdd.setOnClickListener{
                            val intent = Intent(requireContext(), AddToCartActivity::class.java)
                            startActivity(intent)

                        }
                    }
                }
            })
        recyclerView.adapter = rvAdapProgress

    }

   /* private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.content_frame, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }*/

}