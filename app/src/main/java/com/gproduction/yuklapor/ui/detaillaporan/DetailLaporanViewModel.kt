package com.gproduction.yuklapor.ui.detaillaporan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.TanggapanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository
import com.gproduction.yuklapor.data.repository.TanggapanRepository
import java.text.SimpleDateFormat
import java.util.*

class DetailLaporanViewModel : ViewModel() {
    var detailLaporanInterface: DetailLaporanInterface? = null

    var tanggapan:String?=null
    var namaPegawai:String?=null

    var tanggapanModel:TanggapanModel? = null
    var statusSpinner = 0

    fun buttonTanggapanClick(data: LaporkanModel) {
        detailLaporanInterface?.onButtonTanggapanClicked(data)
    }

    fun deleteLaporan(data: LaporkanModel) {
        val hapus = LaporkanRepository().hapusLaporan(data)
        detailLaporanInterface?.onDeleteLaporan(hapus)
    }

    fun buttonUbahStatusClicked(data: LaporkanModel){
       if(statusSpinner == 0){
           detailLaporanInterface?.onButtonStatusClickedFailed()
           return
       }else{
           changeStatusLaporan(statusSpinner,data)
       }
    }

    fun getDetailTanggapan(idTanggapan:String){
        val data = TanggapanRepository().getTanggapanById(idTanggapan)
        detailLaporanInterface?.onGetDetailTanggapan(data)

        tanggapanModel?.let {
            tanggapan = it.tanggapan
        }
    }

    private fun changeStatusLaporan(status:Int,data: LaporkanModel){
        val calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val tanggal = formatter.format(calendar)

        if (tanggapanModel == null){
            tanggapanModel = TanggapanModel(
                null,
                data.id,
                data.uid,
                namaPegawai,
                tanggapan,
                tanggal
            )
        }else{
            tanggapanModel?.tanggapan = tanggapan
        }


        val ref = LaporkanRepository().editStatusLaporan(tanggapanModel!!,status,data)
        detailLaporanInterface?.onButtonStatusClicedSuccess(ref)
    }
}