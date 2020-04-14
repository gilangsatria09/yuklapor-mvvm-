package com.gproduction.yuklapor.ui.laporkan

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.repository.LaporkanRepository
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class LaporkanViewModel : ViewModel() {

    var laporkanInterface: LaporkanInterface? = null

    var photo: Bitmap? = null
    var uid: String? = null
    var nik: String? = null
    var judul: String? = null
    var tanggal: String? = null
    var content: String? = null
    var status: Int? = 0

    fun addPhotoOnClick() {
        laporkanInterface?.onAddPhotoClicked()
    }

    fun cameraOnClick() {
        laporkanInterface?.onCameraClicked()
    }

    fun galleryOnClick(){
        laporkanInterface?.onGalleryClicked()
    }

    fun laporkanOnClick(view: View, model: LaporkanModel?) {
        if (uid.isNullOrEmpty() || judul.isNullOrEmpty() || nik.isNullOrEmpty()) {
            laporkanInterface?.onFailed("Isi Terlebih Dahulu Form Yang Telah Disediakan!")
            return
        }

        //Convert Photo To Byte
        var byte: ByteArray? = null
        if (photo != null) {
            val stream = ByteArrayOutputStream()
            photo!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            byte = stream.toByteArray()
        }

        //Get Current Date
        val calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        tanggal = formatter.format(calendar)

        if (model == null) {
            if (photo == null) {
                laporkanInterface?.onFailed("Foto Harus Ada!")
                return
            }
            val laporkanModel = LaporkanModel(
                null,
                uid,
                null,
                null,
                nik,
                judul,
                content,
                tanggal,
                0
            )

            val register = LaporkanRepository().insertLaporan(byte!!, laporkanModel)
            laporkanInterface?.onLaporkan(register)
        } else {
            if (byte != null) {
                model.let {
                    val laporkanModel = LaporkanModel(
                        it.id,
                        it.uid,
                        it.idTanggapan,
                        it.imageUrl,
                        it.nik,
                        judul,
                        content,
                        it.tanggal,
                        it.status
                    )
                    val update = LaporkanRepository().editLaporan(byte, laporkanModel)
                    laporkanInterface?.onLaporkan(update)
                }
            } else {
                model.let {
                    val laporkanModel = LaporkanModel(
                        it.id,
                        it.uid,
                        it.idTanggapan,
                        it.imageUrl,
                        it.nik,
                        judul,
                        content,
                        it.tanggal,
                        it.status
                    )
                    val update = LaporkanRepository().editLaporan(null,laporkanModel)
                    laporkanInterface?.onLaporkan(update)
                }
            }
        }
    }



}