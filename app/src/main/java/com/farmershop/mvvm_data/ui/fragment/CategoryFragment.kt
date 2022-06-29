package com.farmershop.mvvm_data.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.databinding.CategoryRowHomeBinding
import com.farmershop.mvvm_data.ui.adapter.RecyclerCallback
import com.farmershop.mvvm_data.ui.adapter.RecyclerViewGenricAdapter
import kotlinx.android.synthetic.main.content_lay_mainact.*
import kotlinx.android.synthetic.main.content_lay_mainact.recycler_category_mainAct
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance(): CategoryFragment {
            return  CategoryFragment()
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
        return inflater.inflate(R.layout.fragment_category, container, false)
    }
    private fun initView() {

      /*  layoutCategory2.setOnClickListener {

        }*/
        /*TAG = "FrgHome"
        mContext = activity?.applicationContext!!
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel?.baseInterface = this
        recycler_view_category.layoutManager= GridLayoutManager(mContext,2)
        recycler_view_popular.layoutManager=
            LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false)
        viewModel.categoryList()*/
        // linearLayoutManagerProduct = GridLayoutManager(this, 2)
        recycler_category.layoutManager = GridLayoutManager(requireContext(), 2)
        setAdapterProduct(recycler_category)


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
        val rvAdapProgress = RecyclerViewGenricAdapter<String, CategoryRowHomeBinding>(
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

    fun addFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutCategory, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}