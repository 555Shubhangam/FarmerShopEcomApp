package com.farmershop.data.model.response
data class CartListResponse(
    val `data`: ArrayList<CartListData>,
    val errors: List<Any>,
    val message: String,
    val status: Boolean
)

data class CartListData(
    val brand: String,
    val category: String,
    val category_id: Int,
    val description: String,
    val img_1200_1200: String,
    val img_300_300: String,
    val img_600_600: String,
    val name: String,
    val product_id: Int,
    val product_variant_id: Int,
    val qty: Int,
    val sku: String,
    val sub_category_id: Int,
    val sub_scategory: String
)


