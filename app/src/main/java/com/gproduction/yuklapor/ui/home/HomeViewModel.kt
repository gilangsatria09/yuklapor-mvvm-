package com.gproduction.yuklapor.ui.home

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.gproduction.yuklapor.data.repository.UserRepository
import com.gproduction.yuklapor.ui.daftarlaporan.main.DaftarLaporanActivity

class HomeViewModel(application: Application) : AndroidViewModel(application){

    var homeInterface:HomeInterface? = null

    fun getUserData(uid:String){
        val data = UserRepository().getUserData(uid)
        homeInterface?.getUserData(data)
    }

    fun daftarLaporanOnClick(){
       homeInterface?.onDaftarLaporanClicked()
    }
}