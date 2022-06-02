package com.farmershop.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiPojo {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: DataBin? = null
}

class ApiPojoArray {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: ArrayList<DataBin>? = null
}

class UserList {
    @SerializedName("count")
    @Expose
    var count: String? = null
    @SerializedName("userId")
    @Expose
    var userId: String? = null
}

class PostPojo {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("body")
    @Expose
    var body: String? = null
}


class DataBin {
    @SerializedName("added_to_cart")
    @Expose
    var added_to_cart: String? = null

    @SerializedName("qty")
    @Expose
    var qty: String? = null

    @SerializedName("item_count")
    @Expose
    var item_count: String? = null

    @SerializedName("product_id")
    @Expose
    var product_id: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("old_price")
    @Expose
    var old_price: String? = null

    @SerializedName("category_id")
    @Expose
    var category_id: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("category")
    @Expose
    var category: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("mobile")
    @Expose
    var mobile: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("photo")
    @Expose
    var photo: String? = null

    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = null

    @SerializedName("location")
    @Expose
    var location: String? = null

}
