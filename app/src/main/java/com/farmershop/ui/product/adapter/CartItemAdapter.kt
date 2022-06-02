package com.farmershop.ui.product.adapter

import AppConfig
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.exfactor.appsdk.AppSession
import com.farmershop.R
import com.farmershop.appSDK.StaticMethods
import com.farmershop.data.`interface`.BaseInterface
import com.farmershop.data.network.DataBin
import com.farmershop.ui.product.Cart
import com.farmershop.ui.product.CategoryItems
import com.farmershop.ui.product.ProductDetails
import kotlinx.android.synthetic.main.category_item_row.view.*


class CartItemAdapter(private val mContext: Context, private val list: ArrayList<DataBin>, val parent: Cart) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image
        val product_name = view.product_name
        val price = view.price
        val old_price = view.old_price
        val btnAdd = view.btnAdd
        val qty_layout=view.qty_layout
        val btnPlus=view.btnPlus
        val btnMinus=view.btnMinus
        val qty=view.qty

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.cart_item_row,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=list.get(position)
        StaticMethods.loadImage(
            mContext,
            AppConfig.mediaProduct + data.image,
            holder.image
        )

        holder.qty_layout.visibility=View.GONE
        holder.btnAdd.visibility=View.VISIBLE
        holder.product_name.text =data.name
        holder.price.text ="Rs."+data.price

        holder.old_price.text =data.old_price
        StaticMethods.setStrikeThrough(holder.old_price)
        holder.product_name.setOnClickListener{
            productDetails(data)
        }
        holder.image.setOnClickListener{
            productDetails(data)
        }
        holder.btnAdd.setOnClickListener{
            holder.btnAdd.visibility=View.GONE
            holder.qty_layout.visibility=View.VISIBLE
            parent.updateCart()
        }

        holder.btnPlus.setOnClickListener{
            var qty_value=holder.qty.text.toString().toInt()
            if(qty_value<AppConfig.maxQty){
                qty_value++
            }else{
                Toast.makeText(mContext, "Maximum allowed quantity/order is "+AppConfig.maxQty, Toast.LENGTH_SHORT).show()
            }
            holder.qty.text = qty_value.toString()
        }
        holder.btnMinus.setOnClickListener{
            var qty_value=holder.qty.text.toString().toInt()
            if(qty_value>1){
                qty_value--
            }
            holder.qty.text = qty_value.toString()
            parent.updateCart()
        }
    }

    private fun productDetails(data:DataBin){
        val intent= Intent(mContext, ProductDetails::class.java)
        intent.putExtra("product_id", data.product_id)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }
}

