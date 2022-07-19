package com.farmershop.data.model.response

/**
 * Created by Amit Gupta on 16-05-2021.
 */
data class ForgetPasswordResponse(
    var status: Boolean,
    var message: String,
    var code: Int,
    var data: ForgetPasswordModal
)
data class ForgetPasswordModal(
    val auth_token: String,
    val otp: String
)