package com.gproduction.yuklapor.tools

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.gproduction.yuklapor.R

object BindingAdapter {
    @BindingAdapter(value = ["status","context"],requireAll = false)
    @JvmStatic
    fun setStatusColor(textView: MaterialTextView, status:String,context:Context){
        when (status){
            "0" ->{
                textView.text = context.getString(R.string.belum_diproses)
                textView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.darkGray))
            }
            "1" -> {
                textView.text = context.getString(R.string.diproses)
                textView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.colorBackgroundDark))
            }
            "2" -> {
                textView.text = context.getString(R.string.selesai)
                textView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.colorPrimaryDark))
            }
        }
    }

    @BindingAdapter(value = ["urlSemuaLaporan","context"],requireAll = false)
    @JvmStatic
    fun bindImage(imageView: ImageView,url:String?,context: Context){
        if (url != null){
            Glide.with(context).load(url).into(imageView)
        }
    }
}