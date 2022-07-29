package com.farmershop.data.model.request

data class ChangePasswordRequest(
    val old_password: String,
    val password: String,
    val password_confirmation: String
)
