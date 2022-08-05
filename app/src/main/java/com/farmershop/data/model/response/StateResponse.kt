package com.farmershop.data.model.response

data class StateResponse(
    val `data`: ArrayList<DataState>?,
    val message: String?,
    val status: Boolean?
)

data class DataState(
    val id: Int?,
    val state: String?
)