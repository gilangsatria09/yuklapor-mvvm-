package com.gproduction.yuklapor.tools

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import com.gproduction.yuklapor.R

class CustomAlertDialog(context: Context):AlertDialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_custom_alert_dialog) }
}