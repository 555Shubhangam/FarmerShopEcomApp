package com.farmershop.data.`interface`


interface BaseInterface {
    fun onApiStart()
    fun onApiFailed(message:String)
    fun onDrawerItemClick(int:Int)
    fun onAdapterItemClick(args:Array<String>)
}