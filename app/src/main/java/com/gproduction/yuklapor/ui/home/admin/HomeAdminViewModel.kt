package com.gproduction.yuklapor.ui.home.admin

import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository

class HomeAdminViewModel : ViewModel(){
    var homeAdminInterface:HomeAdminInterface? = null

    fun daftarLaporanOnClick(){
        homeAdminInterface?.onDaftarLaporanClicked()
    }

    fun beritaClicked(){
        homeAdminInterface?.onBeritaClicked()
    }

    fun getAllData(){
        val data = LaporkanRepository().getAllLaporan()
        homeAdminInterface?.onGetAllData(data)
    }

    fun reportClicked(){
        homeAdminInterface?.onReportClicked()
    }
}