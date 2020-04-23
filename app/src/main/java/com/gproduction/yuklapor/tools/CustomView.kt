package com.gproduction.yuklapor.tools

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.Window
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gproduction.yuklapor.R

class CustomView {
    companion object{
        fun bottomSheet(context: Context):BottomSheetDialog{
            val bottomSheetDialog = BottomSheetDialog(context)
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_choose_photo)
            return bottomSheetDialog
        }
        fun dialogCustom(context: Context): Dialog{
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.layout_custom_dialog)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            return dialog
        }
    }
}