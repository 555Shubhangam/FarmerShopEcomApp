package com.farmershop.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.data.model.response.AVariant
import com.farmershop.databinding.ItemDropListBinding


class VariantListAdapter(
    private val mContext: Context,
    private val mListener: onClickListener
) :
    RecyclerView.Adapter<VariantListAdapter.MyViewHolder>() {
    private var popupHelpList: ArrayList<AVariant>? = ArrayList()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemDropListBinding? = DataBindingUtil.bind(itemView)

        init {
            binding?.clsMain?.setOnClickListener {
                mListener.onSelectItem(adapterPosition, popupHelpList!!)
            }
        }

        fun bindItem(mContext: Context, itemList: AVariant) {
            binding?.tvNameItem?.text = itemList.variant_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_drop_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (popupHelpList != null)
            popupHelpList!!.size
        else {
            0
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (popupHelpList?.size!! > 0) {
            val transactionModel = popupHelpList?.get(position)
            holder.bindItem(mContext, transactionModel!!)
        }
    }

    fun setDataList(primalList: ArrayList<AVariant>) {
        this.popupHelpList = primalList
        notifyDataSetChanged()
    }

    interface onClickListener {
        fun onSelectItem(position: Int, arrayList: ArrayList<AVariant>)
    }

}