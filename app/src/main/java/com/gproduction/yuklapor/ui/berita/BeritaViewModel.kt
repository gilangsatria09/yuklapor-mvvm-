package com.gproduction.yuklapor.ui.berita

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.gproduction.yuklapor.data.model.BeritaModel
import com.gproduction.yuklapor.data.repository.BeritaRepository
import com.gproduction.yuklapor.ui.berita.create.CreateBeritaInterface
import com.gproduction.yuklapor.ui.berita.list.ListBeritaInterface
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class BeritaViewModel : ViewModel(){
    var listBeritaInterface: ListBeritaInterface?=null
    var createBeritaInterface: CreateBeritaInterface?=null

    var judul:String? = null
    var content:String? = null
    var tanggal: String? = null
    var photo: Bitmap? = null


    fun getAllBerita(){
        val data = BeritaRepository().getAllBerita()
        listBeritaInterface?.onGetData(data)
    }

    fun cardClicked(data:BeritaModel){
        listBeritaInterface?.onCardClicked(data)
    }

    fun addPhotoOnClick() {
        createBeritaInterface?.onAddPhotoClicked()
    }

    fun buttonBeritaClicked(){
        if (judul.isNullOrEmpty() || content.isNullOrEmpty() || photo == null){
            createBeritaInterface?.onFailed()
            return
        }
        val stream = ByteArrayOutputStream()
        photo!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byte = stream.toByteArray()

        val calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        tanggal = formatter.format(calendar)

        val model = BeritaModel(
            null,
            judul,
            content,
            null,
            tanggal
        )

        val data = BeritaRepository().createBerita(byte,model)
        createBeritaInterface?.onButtonBeritaClicked(data)
    }
}