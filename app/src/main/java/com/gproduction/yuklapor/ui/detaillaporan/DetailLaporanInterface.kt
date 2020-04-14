package com.gproduction.yuklapor.ui.detaillaporan

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.TanggapanModel

interface DetailLaporanInterface {
    fun onButtonTanggapanClicked(data:LaporkanModel)
    fun onDeleteLaporan(data:LiveData<Resource<String>>)
    fun onButtonStatusClickedFailed()
    fun onButtonStatusClicedSuccess(data:LiveData<Resource<Boolean>>)
    fun onGetDetailTanggapan(data: LiveData<Resource<TanggapanModel>>)
}