package com.farmershop.data.model.request

data class ResetPasswordRequest(
    val password: String,
    val password_confirmation: String,
    val username: String
)
