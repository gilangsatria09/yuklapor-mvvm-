package com.gproduction.yuklapor.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.gproduction.yuklapor.data.repository.UserRepository

class AuthViewModel : ViewModel() {
    var email: String? = null
    var nik: String? = null
    var nama: String? = null
    var username: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var noHp: String? = null

    var authInterface: AuthInterface? = null


    fun onButtonClick(view: View) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authInterface?.onFailed("Harap masukan username dan password!")
            return
        }
        val loginResponse = UserRepository().userLogin(email!!, password!!)

        authInterface?.onSuccess(loginResponse)
    }

    fun onRegistrasiClick() {
        authInterface?.buttomSheetState()
    }

    fun onButtonRegistrasiClick(view: View) {
        if (nik?.trim().isNullOrEmpty() || email?.trim().isNullOrEmpty() || username?.trim().isNullOrEmpty() || password?.trim().isNullOrEmpty() || nama?.trim().isNullOrEmpty() || noHp?.trim().isNullOrEmpty()) {
            authInterface?.onFailed("Harap Lengkapi Data yang Ada!")
            return
        }
        if (confirmPassword!! != password!!) {
            authInterface?.onFailed("Password Tidak Sama!")
            return
        }
        if (username!!.contains("@")){
            authInterface?.onFailed("Format username salah!")
            return
        }

        val checkDuplicateData = UserRepository().checkDuplicateData(nik!!,username!!,noHp!!)
        authInterface?.onChecked(checkDuplicateData)

    }

    fun onAllChecked(){
        val firebaseRegister = UserRepository().userRegister(email!!, password!!)
        authInterface?.onRegister(firebaseRegister)
        authInterface?.onLoading()
    }

    fun registerToDatabase(authResult: AuthResult?) {
        authResult?.let {
            val firebaseUser = it.user
            UserRepository().registerToDatabase(
                firebaseUser!!.uid,
                nik!!,
                nama!!,
                email!!,
                username!!,
                noHp!!
            )
        }
    }

    fun isLoggedIn(){
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null){
            authInterface?.onStarted(true)
        }
        else{
            authInterface?.onStarted(false)
        }
    }

    fun getNIK(uid:String){
        val nik = UserRepository().getNIK(uid)
        authInterface?.onGetNik(nik)
    }
}
