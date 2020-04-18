package com.gproduction.yuklapor.ui.profile.detailprofile

import androidx.lifecycle.LiveData
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.UserModel

interface DetailProfileInterface {
    fun onGetData(data:LiveData<Resource<UserModel>>)
}