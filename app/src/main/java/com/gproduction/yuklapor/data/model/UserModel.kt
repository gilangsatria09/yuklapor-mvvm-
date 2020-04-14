package com.gproduction.yuklapor.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val nik:String?=null,
    val nama:String? = null,
    val email:String? = null,
    val noHp:String?=null,
    val role:Int?=null
)