package com.farmershop.data.model.response

data class RegisterResponse(
    val `data`: DataRegister?,
    val message: String?,
    val errors: ArrayList<String>?,
    val status: Boolean?
)

data class DataRegister(
    val token: String
)
