package com.gproduction.yuklapor.ui.profile

import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    var profileInterface:ProfileInterface?=null

    fun cardDetailClicked() {
        profileInterface?.onCardProfileClicked()
    }

    fun cardRegistrasiClicked(){
        profileInterface?.onCardRegistrasiClicked()
    }
}