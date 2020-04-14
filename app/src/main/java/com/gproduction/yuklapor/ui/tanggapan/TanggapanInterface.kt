package com.gproduction.yuklapor.ui.tanggapan

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.TanggapanModel

interface TanggapanInterface {
    fun getDataTanggapan(data:LiveData<Resource<TanggapanModel>>)
}