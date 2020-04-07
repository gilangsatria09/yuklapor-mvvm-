package com.gproduction.yuklapor.data.network.model

import com.gproduction.yuklapor.data.model.UserModel

data class LoginResponse(
    val isSuccessful:Boolean? = null,
    var message:String? =null,
    val user:UserModel? = null
)