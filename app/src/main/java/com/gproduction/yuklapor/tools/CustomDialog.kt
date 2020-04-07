package com.gproduction.yuklapor.tools

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.gproduction.yuklapor.R

class CustomDialog(context: Context):Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_custom_dialog)
        setCanceledOnTouchOutside(false)
    }
}