package com.farmershop.data.model.response

data class AddCartResponse(
    val `data`: List<AddCartData>,
    val errors: List<Any>,
    val message: String,
    val status: Boolean
)

data class AddCartData(
    val product_id: Int,
    val product_name: String,
    val qty: Int,
    val sku: String
)
