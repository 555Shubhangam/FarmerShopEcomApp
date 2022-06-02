package com.farmershop.ui.product.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.appSDK.StaticMethods
import com.farmershop.data.network.DataBin
import com.farmershop.ui.product.CategoryItems
import kotlinx.android.synthetic.main.category_row_home.view.*

class HomeCategoryAdapter(private val mContext: Context, private val list : ArrayList<DataBin>) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image
        val category = view.category
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.category_row_home, parent, false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=list.get(position)
        StaticMethods.loadImage(mContext,AppConfig.mediaCategory+data.image.toString(),holder.image)
        holder.category.text =data.category
        holder.itemView.setOnClickListener{
            val intent= Intent(mContext, CategoryItems::class.java)
            intent.putExtra("category_id",data.category_id)
            intent.putExtra("category",data.category)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent)
        }
    }
}

