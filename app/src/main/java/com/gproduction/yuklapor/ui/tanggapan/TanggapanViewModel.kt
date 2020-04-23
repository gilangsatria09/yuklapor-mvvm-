package com.gproduction.yuklapor.ui.tanggapan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gproduction.yuklapor.data.repository.TanggapanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TanggapanViewModel : ViewModel(){
    var tanggapanInterface: TanggapanInterface? = null

    //Just Testing with Kotlin Coroutines with Dispatcher.IO

    suspend fun fetchTanggapan(id:String){
        val data = TanggapanRepository().get(id)
        tanggapanInterface?.getDataTanggapan(data)
    }

}