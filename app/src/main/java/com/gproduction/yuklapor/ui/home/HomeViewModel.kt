package com.gproduction.yuklapor.ui.home

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import com.google.android.material.textview.MaterialTextView
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository
import com.gproduction.yuklapor.data.repository.UserRepository
import com.gproduction.yuklapor.ui.daftarlaporan.main.DaftarLaporanActivity

class HomeViewModel(application: Application) : AndroidViewModel(application){

    var homeInterface:HomeInterface? = null
    val context:Context = application.applicationContext

    fun getUserData(uid:String){
        val data = UserRepository().getUserData(uid)
        homeInterface?.getUserData(data)
    }

    fun daftarLaporanOnClick(){
       homeInterface?.onDaftarLaporanClicked()
    }

    fun onCardClick(model:LaporkanModel){
        homeInterface?.onCardClicked(model)
    }

    fun getAllDataLaporan(uid:String){
        val data = LaporkanRepository().getAllLaporanByUID(uid)
        homeInterface?.getAllDataLaporan(data)
    }


}