package com.gproduction.yuklapor.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.gproduction.yuklapor.data.repository.UserRepository
import com.gproduction.yuklapor.ui.profile.detailprofile.DetailProfileInterface
import com.gproduction.yuklapor.ui.profile.registrasiadmin.RegistrasiAdminInterface

class ProfileViewModel : ViewModel() {
    var profileInterface:ProfileInterface?=null
    var detailProfileInterface:DetailProfileInterface?=null
    var registrasiAdminInterface:RegistrasiAdminInterface?=null

    var email: String? = null
    var nik: String? = null
    var nama: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var noHp: String? = null
    var role = 0

    fun cardDetailClicked() {
        profileInterface?.onCardProfileClicked()
    }

    fun cardRegistrasiClicked(){
        profileInterface?.onCardRegistrasiClicked()
    }

    fun buttonLogoutClicked(){
        profileInterface?.onButtonLogout()
    }

    fun buttonRegistrasiClicked(){
        if (nik?.trim().isNullOrEmpty() || email?.trim().isNullOrEmpty() || password?.trim().isNullOrEmpty() || nama?.trim().isNullOrEmpty() || noHp?.trim().isNullOrEmpty()) {
            registrasiAdminInterface?.onFailed("Harap Lengkapi Data yang Ada!")
            return
        }
        if (confirmPassword!! != password!!) {
            registrasiAdminInterface?.onFailed("Password Tidak Sama!")
            return
        }

        val checkDuplicateData = UserRepository().checkDuplicateData(nik!!,noHp!!)
        registrasiAdminInterface?.onDataChecked(checkDuplicateData)
    }

    fun onAllChecked(){
        val firebaseRegister = UserRepository().userRegister(email!!, password!!)
        registrasiAdminInterface?.onRegisterAuth(firebaseRegister)
    }

    fun getDataUser(uid:String){
        val data = UserRepository().getUserData(uid)
        detailProfileInterface?.onGetData(data)
    }

    fun registrasiToDatabase(authResult: AuthResult?){
        authResult?.let {
            val firebaseUser = it.user
            UserRepository().registerToDatabase(
                firebaseUser!!.uid,
                nik!!,
                nama!!,
                email!!,
                noHp!!,
                role
            )
        }
    }
}