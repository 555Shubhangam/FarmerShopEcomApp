package com.farmershop.data.model.request

data class RegisterRequest (
    var name: String ,
    var address: String ,
    var mobile: String ,
    var username: String ,
    var email: String ,
    var password: String,
    var password_confirmation: String
   // var device_type_id: String,
   // var device_id: String,
  //  var device_token: String
)