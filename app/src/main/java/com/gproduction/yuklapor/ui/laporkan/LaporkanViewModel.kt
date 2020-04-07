package com.gproduction.yuklapor.ui.laporkan

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class LaporkanViewModel:ViewModel() {

    var laporkanInterface:LaporkanInterface? = null

    var photo: Bitmap? = null
    var uid:String? = null
    var nik:String? = null
    var judul:String? = null
    var tanggal:String?=null
    var content:String? = null

    fun addPhotoOnClick(){
        laporkanInterface?.onAddPhotoClicked()
    }

    fun cameraOnClick(){
        laporkanInterface?.onCameraClicked()
    }

    fun laporkanOnClick(){
        if (uid.isNullOrEmpty() || photo == null || judul.isNullOrEmpty() || nik.isNullOrEmpty()){
            laporkanInterface?.onFailed()
            return
        }

        //Convert Photo To Byte
        val stream = ByteArrayOutputStream()
        photo!!.compress(Bitmap.CompressFormat.JPEG,100,stream)
        val byte = stream.toByteArray()

        //Get Current Date
        val calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
        tanggal = formatter.format(calendar)

        val model = LaporkanModel(
            uid,
            null,
            nik,
            judul,
            content,
            tanggal,
            0
        )

        val register = LaporkanRepository().insertLaporan(byte,model)
        laporkanInterface?.onLaporkan(register)
    }

}