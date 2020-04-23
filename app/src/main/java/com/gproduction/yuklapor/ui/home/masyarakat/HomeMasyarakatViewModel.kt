package com.gproduction.yuklapor.ui.home.masyarakat

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository

class HomeMasyarakatViewModel(application: Application) : AndroidViewModel(application){

    var homeMasyarakatInterface: HomeMasyarakatInterface? = null
    val context:Context = application.applicationContext


    fun daftarLaporanOnClick(){
       homeMasyarakatInterface?.onDaftarLaporanClicked()
    }

    fun beritaClicked(){
        homeMasyarakatInterface?.onBeritaClicked()
    }

    fun getAllDataLaporanByUID(uid:String){
        val data = LaporkanRepository().getAllLaporanByUID(uid)
        homeMasyarakatInterface?.getAllDataLaporan(data)
    }
    fun getAllData(){
        val data = LaporkanRepository().getAllLaporan()
        homeMasyarakatInterface?.getAllDataLaporan(data)
    }

}