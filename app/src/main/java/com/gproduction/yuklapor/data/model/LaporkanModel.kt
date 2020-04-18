package com.gproduction.yuklapor.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaporkanModel(
    var id:String?=null,
    val uid:String?=null,
    var idTanggapan:String?=null,
    var imageUrl:String?=null,
    val nik:String?=null,
    val judul:String?=null,
    val content:String?=null,
    val tanggal:String?=null,
    var status:Int?=null,
    var namaPembuat:String?=null
) : Parcelable