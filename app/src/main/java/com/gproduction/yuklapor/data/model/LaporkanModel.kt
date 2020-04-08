package com.gproduction.yuklapor.data.model

data class LaporkanModel(
    var id:String?=null,
    val uid:String?=null,
    var imageUrl:String?=null,
    val nik:String?=null,
    val judul:String?=null,
    val content:String?=null,
    val tanggal:String?=null,
    val status:String?=null
)