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
import com.gproduction.yuklapor.data.model.BeritaModel
import com.gproduction.yuklapor.tools.BERITA
import com.gproduction.yuklapor.tools.STORAGE_IMAGE

class BeritaRepository {
    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    private val database by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun createBerita(byte: ByteArray, model: BeritaModel): LiveData<Resource<Boolean>> {
        val id = "${database.push().key}"
        val data = MutableLiveData<Resource<Boolean>>()
        data.value = Resource.loading(null)
        val ref = storage.child(STORAGE_IMAGE).child(BERITA).child(id)

        ref.putBytes(byte).apply {
            addOnCompleteListener {
                ref.downloadUrl.apply {
                    addOnCompleteListener {
                        model.imageURL = it.result.toString()
                        database.child(BERITA).child(id).setValue(model)
                        data.value = Resource.success(true)
                    }
                }
            }
            addOnFailureListener {
                data.value = Resource.error("Gagal", false)
            }

        }
        return data
    }

    fun getAllBerita(): LiveData<Resource<ArrayList<BeritaModel>>> {
        val ref = database.child(BERITA)
        val listData = ArrayList<BeritaModel>()
        val data = MutableLiveData<Resource<ArrayList<BeritaModel>>>()
        data.value = Resource.loading(null)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (i in p0.children) {
                    val beritaModel: BeritaModel? = i.getValue(BeritaModel::class.java)
                    beritaModel?.id = i.key

                    beritaModel?.let {
                        listData.add(it)
                    }
                }
                if (listData.size > 0) {
                    data.value = Resource.success(listData)
                    Log.d("DATA", "$listData")
                } else {
                    data.value = Resource.error("Data Tidak Ada!", null)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                data.value = Resource.error("Gagal", null)
            }
        })
        return data
    }
}