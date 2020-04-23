package com.gproduction.yuklapor.ui.home.masyarakat

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel

interface HomeMasyarakatInterface {
    fun onDaftarLaporanClicked()
    fun onBeritaClicked()
    fun getAllDataLaporan(data:LiveData<Resource<ArrayList<LaporkanModel>>>)
}