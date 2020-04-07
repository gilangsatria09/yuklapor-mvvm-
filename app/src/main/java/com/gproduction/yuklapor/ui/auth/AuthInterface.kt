package com.gproduction.yuklapor.ui.auth

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.network.model.LoginResponse

interface AuthInterface {
    fun onStarted(isLoggedIn:Boolean)
    fun onSuccess(loginResponse: LiveData<Resource<AuthResult>>)
    fun onRegister(firebaseUser: LiveData<Resource<AuthResult>>)
    fun onLoading()
    fun onFailed(pesan:String)
    fun buttomSheetState()
    fun onChecked(boolean:LiveData<Resource<Boolean>>)
    fun onGetNik(string:LiveData<String>)

}