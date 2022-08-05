package com.farmershop.data.model.request

data class OTPRequest(
    val username: String,
    val otp: String,
    val otp_for: String
)
