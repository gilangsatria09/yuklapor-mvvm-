package com.gproduction.yuklapor.ui.daftarlaporan

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository
import com.gproduction.yuklapor.ui.daftarlaporan.main.InterfaceListener

class DaftarLaporanViewModel(application: Application) : AndroidViewModel(application) {
    var daftarLaporanInterface:DaftarLaporanInterface? = null
    var interfaceLaporan:InterfaceListener? = null


    fun getAllData(uid:String){
        val data = LaporkanRepository().getAllLaporanByUID(uid)
        daftarLaporanInterface?.onSuccess(data)
    }
    fun getAllData(){
        val data = LaporkanRepository().getAllLaporan()
        daftarLaporanInterface?.onSuccess(data)
    }

    fun getDataDiproses(uid: String){
        val data = LaporkanRepository().getDataDiproses(uid)
        daftarLaporanInterface?.onSuccess(data)
    }
    fun getDataDiproses(){
        val data = LaporkanRepository().getDataDiproses()
        daftarLaporanInterface?.onSuccess(data)
    }

    fun getDataSelesai(uid:String){
        val data = LaporkanRepository().getDataSelesai(uid)
        daftarLaporanInterface?.onSuccess(data)
    }
    fun getDataSelesai(){
        val data = LaporkanRepository().getDataSelesai()
        daftarLaporanInterface?.onSuccess(data)
    }

    fun floatingButtonClicked(){
        interfaceLaporan?.onFloatingClicked()
    }

    fun onCardClicked(data:LaporkanModel){
        daftarLaporanInterface?.onCardClicked(data)
    }

}