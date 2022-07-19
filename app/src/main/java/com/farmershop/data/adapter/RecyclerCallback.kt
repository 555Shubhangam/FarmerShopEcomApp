package com.farmershop.data.adapter

import android.view.View
import androidx.databinding.ViewDataBinding

interface RecyclerCallback<VM : ViewDataBinding, T> {
    fun bindData(binder: VM, model: T, position: Int, itemView: View)
    fun getFilterChar(row: T, s: String): Boolean{
        return false
    }
}
