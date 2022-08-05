package com.farmershop.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.farmershop.R
import com.farmershop.data.model.PopupDropDownListModel
import com.farmershop.databinding.ItemDropListBinding


class PopupDropDownListAdapter(
    private val mContext: Context,
    private val mListener: onClickListener
) :
    RecyclerView.Adapter<PopupDropDownListAdapter.MyViewHolder>() {
    private var popupHelpList: ArrayList<PopupDropDownListModel>? = ArrayList()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemDropListBinding? = DataBindingUtil.bind(itemView)

        init {
            binding?.clsMain?.setOnClickListener {
                mListener.onSelectItem(adapterPosition, popupHelpList!!)
            }
        }

        fun bindItem(mContext: Context, itemList: PopupDropDownListModel) {
            binding?.tvNameItem?.text = itemList.name
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

    fun setDataList(primalList: ArrayList<PopupDropDownListModel>) {
        this.popupHelpList = primalList
        notifyDataSetChanged()
    }

    interface onClickListener {
        fun onSelectItem(position: Int, arrayList: ArrayList<PopupDropDownListModel>)
    }

}