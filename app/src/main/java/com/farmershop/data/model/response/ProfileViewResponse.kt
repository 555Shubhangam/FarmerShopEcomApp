package com.farmershop.data.model.response

data class ProfileViewResponse(
    val `data`: DataProfile,
    val message: String,
    val status: Boolean
)

data class DataProfile(
    val aadhar_no: Any?,
    val address: String?,
    val city: String?,
    val dob: String?,
    val email: String?,
    val gender: String?,
    val id: Int?,
    val mobile: String?,
    val name: String?,
    val photo: String?,
    val state_id: Int?,
    val state_name: String?,
    val username: String?,
    val zip: Int?
)