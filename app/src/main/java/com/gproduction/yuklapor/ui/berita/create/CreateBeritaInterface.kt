package com.gproduction.yuklapor.ui.berita.create

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource

interface CreateBeritaInterface {
    fun onAddPhotoClicked()
    fun onFailed()
    fun onButtonBeritaClicked(data:LiveData<Resource<Boolean>>)
}