package com.farmershop.data.model.response

data class SearchResponse(
    val `data`: ArrayList<SearchData>,
    val errors: List<Any>,
    val message: String,
    val status: Boolean
)

data class SearchData(
    val aVariant: ArrayList<AVariant>,
    val brand: String,
    val category: String,
    val category_id: Int,
    val description: String,
    val discount: Int,
    val discount_percent: String,
    val img_1200_1200: String,
    val img_300_300: String,
    val img_600_600: String,
    val in_cart: Int,
    val in_wish_list: Int,
    val name: String,
    val old_price: String,
    val price: String,
    val product_id: Int,
    val product_variant_id: Int,
    val sku: String,
    val status: String,
    val sub_category_id: Int,
    val sub_scategory: String
)

data class AVariant(
    val is_default: String,
    val old_price: String,
    val price: String,
    val status: String,
    val variant_id: Int,
    val variant_name: String,
    val variant_size: Int
)
