package com.farmershop.ui.fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.farmershop.R
import com.farmershop.data.adapter.RecyclerCallback
import com.farmershop.data.adapter.RecyclerViewGenricAdapter
import com.farmershop.data.adapter.SlidderBannerAdapter
import com.farmershop.data.model.response.BannerData
import com.farmershop.data.viewModel.HomeViewModal
import com.farmershop.databinding.CategoryRowHomeBinding
import com.farmershop.databinding.FragmentHome2Binding
import com.farmershop.databinding.ProductOffersBinding
import com.farmershop.ui.activity.AddToCartActivity
import com.farmershop.ui.activity.ProductDescriptionActivity
import com.farmershop.ui.base.BaseFragment
import com.farmershop.utils.ProgressDialog
import com.farmershop.utils.Resource
import kotlinx.android.synthetic.main.content_lay_mainact.*
import java.util.*


private const val TAG = "HomeFragment"
class HomeFragment : BaseFragment(){
    var bannerList:ArrayList<BannerData> = ArrayList()
    lateinit var binding: FragmentHome2Binding
    private lateinit var viewModal: HomeViewModal
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home2, container, false)
        viewModal = ViewModelProvider(this).get(HomeViewModal::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObserver()
    }

    private fun setObserver() {
        viewModal.bannerResponse.observe(requireActivity()) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    bannerList.clear()
                    //bannerList = response.data?.data!!
                    bannerList.add(BannerData(1,"https://www.verywellfit.com/thmb/8TA9oyVqBqq_Cz7QHOrP5vzJlFE=/1500x1000/filters:fill(FFDB5D,1)/sweet-potato_annotated-5bb037b24c1640d38994e20e355d8818.jpg","","Sweet Potato 20% Discount"))
                    bannerList.add(BannerData(2,"https://femina.wwmindia.com/thumb/content/2019/dec/benefits-of-carrots1575626304.jpg?width=1200&height=900","","Carrot 20Rs OFF"))
                    bannerList.add(BannerData(3,"https://www.thoughtco.com/thmb/q_mXCoQzbVeZln1zhipGlJ-5rRo=/2296x1291/smart/filters:no_upscale()/full-frame-shot-of-onions-in-market-stall-562386223-59b97e59845b340010f8d76e.jpg","","Onion 10% Discount"))
                    bannerList.add(BannerData(4,"https://www.tastingtable.com/img/gallery/the-biggest-mistakes-you-need-to-avoid-when-cooking-tomatoes/l-intro-1657814969.jpg","","Tomato 10Rs OFF"))
                    bannerList.add(BannerData(5,"https://media.istockphoto.com/photos/bell-pepper-fresh-green-red-and-yellow-picture-id479891136?k=20&m=479891136&s=612x612&w=0&h=wySmpjruBECKLfcaCInRws_8C15u7OWXA-HckpdmpXM=","","Capsicum 50% Discount"))
                    Log.wtf("BannerrLisst", "ressponse list --- : " + response.data?.data.toString())
                    val itemsPagerAdapter = SlidderBannerAdapter(requireContext(), bannerList)
                    binding.myPager.adapter = itemsPagerAdapter
                    // The_slide_timer
                    val timer = Timer()
                    timer.scheduleAtFixedRate(The_slide_timer(binding.myPager,bannerList), 2000, 3000)
                    binding.myTablayout.setupWithViewPager(binding.myPager, true)
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(requireContext())
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initView() {
        viewModal.getBanner()
        layoutCategory.setOnClickListener {
            addFragment(CategoryFragment.newInstance())

        }
        layoutCategory1.setOnClickListener {
            addFragment(ProductFragment.newInstance())

        }
        layoutCategory2.setOnClickListener {
            addFragment(ProductFragment.newInstance())

        }
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
   class The_slide_timer(val page: ViewPager,val bannerList:ArrayList<BannerData>) : TimerTask() {
       override fun run() {
           val handler = Handler(Looper.getMainLooper())
           handler.post {
               // UI code goes here
               if (page.currentItem < bannerList.size - 1) {
                   page.currentItem = page.currentItem + 1
               } else page.currentItem = 0
           }
       }
   }
}