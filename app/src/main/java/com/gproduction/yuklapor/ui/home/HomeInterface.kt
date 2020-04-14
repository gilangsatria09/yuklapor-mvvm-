package com.gproduction.yuklapor.ui.home

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.UserModel

interface HomeInterface {
    fun onDaftarLaporanClicked()
    fun onBeritaClicked()
    fun getAllDataLaporan(data:LiveData<Resource<ArrayList<LaporkanModel>>>)
    fun onCardClicked(model:LaporkanModel)
}