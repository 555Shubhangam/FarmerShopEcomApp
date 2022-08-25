package com.farmershop.data.model.request

data class AddCartRequest(
    val customer_id: Int,
    val product_variant_id: Int,
    val qty: Int
)
