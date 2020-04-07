package com.gproduction.yuklapor.ui.home

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.model.UserModel

interface HomeInterface {
    fun getUserData(userData:LiveData<UserModel>)
    fun onDaftarLaporanClicked()
}