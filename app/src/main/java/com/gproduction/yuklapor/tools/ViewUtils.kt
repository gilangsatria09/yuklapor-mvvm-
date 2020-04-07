package com.gproduction.yuklapor.tools

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(pesan:String){
    Toast.makeText(this,pesan,Toast.LENGTH_LONG).show()
}

