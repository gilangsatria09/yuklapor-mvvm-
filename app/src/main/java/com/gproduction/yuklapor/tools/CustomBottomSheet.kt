package com.gproduction.yuklapor.tools

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gproduction.yuklapor.R

class CustomBottomSheet {
    companion object{
        fun bottomSheet(context: Context):BottomSheetDialog{
            val bottomSheetDialog = BottomSheetDialog(context)
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_choose_photo)
            return bottomSheetDialog
        }
    }
}