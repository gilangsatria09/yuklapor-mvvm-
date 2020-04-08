package com.gproduction.yuklapor.ui.daftarlaporan

import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository

class DaftarLaporanViewModel : ViewModel() {
    var daftarLaporanInterface:DaftarLaporanInterface? = null

    fun getAllData(uid:String){
        val data = LaporkanRepository().getAllLaporanByUID(uid)
        daftarLaporanInterface?.onSuccess(data)
    }

    fun getDataDiproses(uid: String){
        val data = LaporkanRepository().getDataDiproses(uid)
        daftarLaporanInterface?.onSuccess(data)
    }
    fun getDataSelesai(uid:String){
        val data = LaporkanRepository().getDataSelesai(uid)
        daftarLaporanInterface?.onSuccess(data)
    }
}