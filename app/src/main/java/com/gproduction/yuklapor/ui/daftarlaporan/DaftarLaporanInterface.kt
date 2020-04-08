package com.gproduction.yuklapor.ui.daftarlaporan

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel

interface DaftarLaporanInterface{
    fun onSuccess(data:LiveData<Resource<ArrayList<LaporkanModel>>>)
}