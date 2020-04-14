package com.gproduction.yuklapor.tools

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(pesan:String){
    Toast.makeText(this,pesan,Toast.LENGTH_LONG).show()
}

fun Context.showDialog() : Dialog{
    Log.d("Test","$this")
    return CustomDialog(this)
}


