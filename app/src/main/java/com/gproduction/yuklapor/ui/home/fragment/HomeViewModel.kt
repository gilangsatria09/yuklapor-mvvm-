package com.gproduction.yuklapor.ui.home.fragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository
import com.gproduction.yuklapor.ui.home.fragment.HomeInterface

class HomeViewModel(application: Application) : AndroidViewModel(application){

    var homeInterface: HomeInterface? = null
    val context:Context = application.applicationContext


    fun daftarLaporanOnClick(){
       homeInterface?.onDaftarLaporanClicked()
    }

    fun beritaClicked(){
        homeInterface?.onBeritaClicked()
    }

    fun onCardClick(model:LaporkanModel){
        homeInterface?.onCardClicked(model)
    }

    fun getAllDataLaporanByUID(uid:String){
        val data = LaporkanRepository().getAllLaporanByUID(uid)
        homeInterface?.getAllDataLaporan(data)
    }
    fun getAllData(){
        val data = LaporkanRepository().getAllLaporan()
        homeInterface?.getAllDataLaporan(data)
    }

}