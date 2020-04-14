package com.gproduction.yuklapor.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TanggapanModel(
    var id:String?=null,
    val idLaporan:String?=null,
    val idUser:String?=null,
    val namaPegawai:String?=null,
    var tanggapan:String?=null,
    val tanggal:String?=null
) : Parcelable