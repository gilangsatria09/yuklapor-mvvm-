package com.gproduction.yuklapor.data.model

data class LaporkanModel(
    var uid:String?=null,
    var imageUrl:String?=null,
    var nik:String?=null,
    var judul:String?=null,
    var content:String?=null,
    var tanggal:String?=null,
    var status:Int?=null
)