package com.farmershop.data.model.request

data class UpdateProfileRequest(
    val address: String,
    val city: String,
    val dob: String,
    val gender: String,
    val name: String,
    val state: String,
    val zip: String
)