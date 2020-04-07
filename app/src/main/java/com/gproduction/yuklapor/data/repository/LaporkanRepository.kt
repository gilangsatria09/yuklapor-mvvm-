package com.gproduction.yuklapor.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.tools.LAPORAN
import com.gproduction.yuklapor.tools.STORAGE_IMAGE
import com.gproduction.yuklapor.tools.STORAGE_LAPORAN

class LaporkanRepository {
    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    private val database by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun insertLaporan(byte:ByteArray, model:LaporkanModel) : LiveData<Int>{
        val ref = storage.child(STORAGE_IMAGE).child(STORAGE_LAPORAN).child(model.uid!!).child(model.judul!!)
        val id = "${database.push().key}"
        val int = MutableLiveData<Int>()

        ref.putBytes(byte).apply {
            addOnCompleteListener {
                ref.downloadUrl.apply {
                    addOnCompleteListener {
                        model.imageUrl = it.result.toString()
                        //Prevents multiple UID
                        database.child(LAPORAN).child(model.uid!!).child(id).setValue(model)
                        int.value = 1
                    }
                }
            }
            addOnFailureListener {
                int.value = 2
            }
        }
        return int
    }
}