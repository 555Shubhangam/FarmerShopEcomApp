package com.farmershop.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.farmershop.R
import com.farmershop.data.model.response.BannerData


class SlidderBannerAdapter(
    private val context: Context,
    private val list: ArrayList<BannerData>
) :
    PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val sliderLayout: View = inflater.inflate(R.layout.item_banner, null)
        val featured_image = sliderLayout.findViewById<ImageView>(R.id.my_featured_image)
        val caption_title = sliderLayout.findViewById<TextView>(R.id.my_caption_title)
        Glide.with(context).load(list[position].image).into(featured_image)
        caption_title.text = list[position].title
        container.addView(sliderLayout)
        return sliderLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

}