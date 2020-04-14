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
    fun runTanggapanScope(id:String){
        viewModelScope.launch {
            fetchTanggapan(id)
        }
    }

    suspend fun fetchTanggapan(id:String){
        val data = getTanggapan(id)
        tanggapanInterface?.getDataTanggapan(data)
    }

    suspend fun getTanggapan(id:String) = withContext(Dispatchers.IO) {
        TanggapanRepository().getTanggapanById(id)
    }

}