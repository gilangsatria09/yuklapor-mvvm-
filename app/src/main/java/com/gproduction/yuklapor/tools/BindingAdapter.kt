package com.gproduction.yuklapor.tools

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.gproduction.yuklapor.R

object BindingAdapter {
    @BindingAdapter(value = ["status","context"],requireAll = false)
    @JvmStatic
    fun setStatusColor(textView: MaterialTextView, status:Int,context:Context){
        when (status){
            0 ->{
                textView.text = context.getString(R.string.belum_diproses)
                textView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.darkGray))
            }
            1 -> {
                textView.text = context.getString(R.string.diproses)
                textView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.colorBackgroundDark))
            }
            2 -> {
                textView.text = context.getString(R.string.selesai)
                textView.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.colorPrimaryDark))
            }
        }
    }

    @BindingAdapter(value = ["role","context"],requireAll = false)
    @JvmStatic
    fun setTextRole(textView: MaterialTextView, status:Int,context:Context){
        when (status){
            0 ->{
                textView.text = context.getString(R.string.masyarakat)
            }
            1 -> {
                textView.text = context.getString(R.string.admin)
            }
            2 -> {
                textView.text = context.getString(R.string.petugas)
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
    @BindingAdapter(value = ["buttonVisibility","context"],requireAll = false)
    @JvmStatic
    fun buttonVisibility(button: Button,status: Int,context: Context){
        val sharPref = SharedPreferences(context)

        if (status == 2 && sharPref.getRole() == 0) {
            button.visibility = View.VISIBLE
        }else{
            button.visibility = View.GONE
        }
    }
}