package com.gproduction.yuklapor.ui.profile.registrasiadmin

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import com.gproduction.yuklapor.data.Resource

interface RegistrasiAdminInterface {
    fun onFailed(pesan:String)
    fun onDataChecked(data: LiveData<Resource<Boolean>>)
    fun onRegisterAuth(data: LiveData<Resource<AuthResult>>)
}