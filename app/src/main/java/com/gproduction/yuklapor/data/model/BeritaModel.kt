package com.gproduction.yuklapor.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeritaModel(
    var id:String?=null,
    val judul:String?=null,
    val content:String?=null,
    var imageURL:String?=null,
    val tanggal:String?=null
):Parcelable