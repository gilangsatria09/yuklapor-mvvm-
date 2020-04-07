package com.gproduction.yuklapor.ui.laporkan

import androidx.lifecycle.LiveData

interface LaporkanInterface {
    fun onFailed()
    fun onLaporkan(data:LiveData<Int>)
    fun onAddPhotoClicked()
    fun onCameraClicked()
}