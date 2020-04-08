package com.gproduction.yuklapor.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.tools.LAPORAN
import com.gproduction.yuklapor.tools.STATUS
import com.gproduction.yuklapor.tools.STORAGE_IMAGE
import com.gproduction.yuklapor.tools.STORAGE_LAPORAN

class LaporkanRepository {
    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    private val database by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun insertLaporan(byte: ByteArray, model: LaporkanModel): LiveData<Int> {
        val ref = storage.child(STORAGE_IMAGE).child(STORAGE_LAPORAN).child(model.uid!!)
            .child(model.judul!!)
        val id = "${database.push().key}"
        val int = MutableLiveData<Int>()

        ref.putBytes(byte).apply {
            addOnCompleteListener {
                ref.downloadUrl.apply {
                    addOnCompleteListener {
                        model.imageUrl = it.result.toString()
                        database.child(LAPORAN).child(model.uid).child(id).setValue(model)
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

    fun getAllLaporanByUID(uid: String): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(LAPORAN).child(uid)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
                    laporkanModel?.id = i.key

                    laporkanModel?.let {
                        listData.add(it)
                    }
                }
                if (listData.size > 0) {
                    data.value = Resource.success(listData)
                } else {
                    data.value = Resource.error("Data Tidak Ada", null)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                data.value = Resource.error("Dicancel", null)
            }
        })
        return data
    }

    fun getDataDiproses(uid: String): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(LAPORAN).child(uid).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children){
                    if (i.child(STATUS).value == "1"){
                        val laporkanModel:LaporkanModel? = i.getValue(LaporkanModel::class.java)
                        laporkanModel?.id = i.key

                        laporkanModel?.let {
                            listData.add(it)
                        }
                    }
                }
                if (listData.size > 0) {
                    data.value = Resource.success(listData)
                } else {
                    data.value = Resource.error("Data Tidak Ada", null)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                data.value = Resource.error("Dicancel", null)
            }
        })
        return data
    }
    fun getDataSelesai(uid: String): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(LAPORAN).child(uid).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children){
                    if (i.child(STATUS).value == "2"){
                        val laporkanModel:LaporkanModel? = i.getValue(LaporkanModel::class.java)
                        laporkanModel?.id = i.key

                        laporkanModel?.let {
                            listData.add(it)
                        }
                    }
                }
                if (listData.size > 0) {
                    data.value = Resource.success(listData)
                } else {
                    data.value = Resource.error("Data Tidak Ada", null)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                data.value = Resource.error("Dicancel", null)
            }
        })
        return data
    }

}