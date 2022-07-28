package com.farmershop.data.model.response

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)

data class Data(
    val id: Int,
    val name: String,
    val token: String
)