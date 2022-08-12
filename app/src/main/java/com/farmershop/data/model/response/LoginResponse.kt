package com.farmershop.data.model.response

data class LoginResponse(
    val `data`: Data,
    val message: String,
    val errorsList: ArrayList<String>?,
    val status: Boolean
)

data class Data(
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
    val token: String?,
    val username: String?,
    val zip: Int?
)

