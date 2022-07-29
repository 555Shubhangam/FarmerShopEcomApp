package com.farmershop.data.model.response

data class ProfileViewResponse(
    val `data`: DataProfile?,
    val message: String?="",
    val status: Boolean?
)

data class DataProfile(
    val aadhar_no: Any?="",
    val address: String?="",
    val dob: Any?="",
    val email: String?="",
    val gender: String?="",
    val mobile: String?="",
    val name: String?="",
    val username: String?=""
)