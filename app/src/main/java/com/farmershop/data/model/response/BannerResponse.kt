package com.farmershop.data.model.response

data class BannerResponse(
    val data: ArrayList<BannerData>,
    val errors: List<Any>,
    val message: String,
    val status: Boolean
)

data class BannerData(
    val id: Int,
    val image: String,
    val redirect_url: String,
    val title: String
)
