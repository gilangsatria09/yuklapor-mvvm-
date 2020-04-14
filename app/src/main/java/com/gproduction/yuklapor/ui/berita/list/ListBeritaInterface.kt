package com.gproduction.yuklapor.ui.berita.list

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.BeritaModel

interface ListBeritaInterface {
    fun onGetData(data:LiveData<Resource<ArrayList<BeritaModel>>>)
    fun onCardClicked(data:BeritaModel)
}