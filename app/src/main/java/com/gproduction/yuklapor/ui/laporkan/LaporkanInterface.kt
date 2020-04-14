package com.gproduction.yuklapor.ui.laporkan

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource

interface LaporkanInterface {
    fun onFailed(message:String)
    fun onLaporkan(data:LiveData<Resource<Int>>)
    fun onAddPhotoClicked()
    fun onCameraClicked()
    fun onGalleryClicked()
}