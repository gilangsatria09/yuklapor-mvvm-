package com.gproduction.yuklapor.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.TanggapanModel
import com.gproduction.yuklapor.tools.TANGGAPAN

class TanggapanRepository {

    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    private val database by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun getTanggapanById(id: String): LiveData<Resource<TanggapanModel>> {
        val data = MutableLiveData<Resource<TanggapanModel>>()
        database.child(TANGGAPAN).child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val tanggapanModel: TanggapanModel? = p0.getValue(TanggapanModel::class.java)
                tanggapanModel?.id = p0.key
                data.value = Resource.success(tanggapanModel)
            }

            override fun onCancelled(p0: DatabaseError) {
                data.value = Resource.error("Gagal", null)
            }
        })
        return data
    }
}