package com.farmershop.data.model.response

data class RegisterResponse(
    val `data`: DataRegister,
    val message: String,
    val status: Int,
    val status_text: String
)

data class DataRegister(
    val token: String
)
