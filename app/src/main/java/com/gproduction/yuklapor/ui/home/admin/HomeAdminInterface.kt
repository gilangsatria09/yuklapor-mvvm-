package com.gproduction.yuklapor.ui.home.admin

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel

interface HomeAdminInterface {
    fun onDaftarLaporanClicked()
    fun onBeritaClicked()
    fun onGetAllData(data:LiveData<Resource<ArrayList<LaporkanModel>>>)
    fun onReportClicked()
}