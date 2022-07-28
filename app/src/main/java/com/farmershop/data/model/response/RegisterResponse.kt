package com.farmershop.data.model.response

data class RegisterResponse(
    val `data`: DataRegister,
    val message: String,
    val status: Boolean,
    val status_text: String
)

data class DataRegister(
    val token: String
)
