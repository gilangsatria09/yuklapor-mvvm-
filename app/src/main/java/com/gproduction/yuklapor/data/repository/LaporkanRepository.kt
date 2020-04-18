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
import com.gproduction.yuklapor.data.model.LaporkanModel
import com.gproduction.yuklapor.data.model.TanggapanModel
import com.gproduction.yuklapor.tools.*

class LaporkanRepository {
    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    private val database by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun insertLaporan(byte: ByteArray, model: LaporkanModel): LiveData<Resource<Int>> {
        val ref = storage.child(STORAGE_IMAGE).child(STORAGE_LAPORAN).child(model.uid!!)
            .child(model.judul!!)
        val id = "${database.push().key}"
        val int = MutableLiveData<Resource<Int>>()
        int.value = Resource.loading(null)

        model.id = id

        ref.putBytes(byte).apply {
            addOnCompleteListener {
                ref.downloadUrl.apply {
                    addOnCompleteListener {
                        model.imageUrl = it.result.toString()
                        database.child(LAPORAN).child(model.uid).child(id).setValue(model)
                        database.child(ALL_LAPORAN).child(id).setValue(model)
                        int.value = Resource.success(1)
                    }
                }
            }
            addOnFailureListener {
                int.value = Resource.error("Gagal", null)
            }
        }
        return int
    }

    fun editLaporan(byte: ByteArray?, model: LaporkanModel): LiveData<Resource<Int>> {
        val ref = storage.child(STORAGE_IMAGE).child(STORAGE_LAPORAN).child(model.uid!!)
        val int = MutableLiveData<Resource<Int>>()
        int.value = Resource.loading(null)

        if (byte != null) {
            if (model.imageUrl.isNullOrEmpty()) FirebaseStorage.getInstance().getReferenceFromUrl(
                model.imageUrl!!
            ).delete()
            ref.putBytes(byte).apply {
                addOnCompleteListener {
                    ref.downloadUrl.apply {
                        addOnCompleteListener {
                            model.imageUrl = it.result.toString()
                            database.child(LAPORAN).child(model.uid).child(model.id!!)
                                .setValue(model)
                            int.value = Resource.success(1)
                        }
                    }
                }
                addOnFailureListener {
                    int.value = Resource.error("Gagal", null)
                }
            }
        } else {
            database.child(LAPORAN).child(model.uid).child(model.id!!).setValue(model).apply {
                addOnCompleteListener {
                    int.value = Resource.success(1)
                }
                addOnFailureListener {
                    int.value = Resource.error("Gagal", null)
                }
            }
        }
        return int
    }

    fun hapusLaporan(model: LaporkanModel): LiveData<Resource<String>> {
        val ref = database.child(LAPORAN).child(model.uid!!).child(model.id!!).removeValue()
        val string = MutableLiveData<Resource<String>>()
        string.value = Resource.loading(null)

        ref.apply {
            addOnCompleteListener {
                FirebaseStorage.getInstance().getReferenceFromUrl(model.imageUrl!!).apply {
                    addOnCompleteListener {
                        string.value = Resource.success("Data Berhasil Dihapus!")
                    }
                }
            }
            addOnFailureListener {
                string.value = Resource.error("Data Gagal Dihapus!", null)
            }
        }
        return string
    }

    fun editStatusLaporan(tanggapan:TanggapanModel,status:Int,model:LaporkanModel): LiveData<Resource<Boolean>>{
        val data = MutableLiveData<Resource<Boolean>>()
        data.value = Resource.loading(null)
        val idTanggapan = "${database.push().key}"

        if (status == 1){
            model.status = 1
            database.child(ALL_LAPORAN).child(model.id!!).setValue(model)
            database.child(LAPORAN).child(model.uid!!).child(model.id!!).setValue(model)
                .addOnCompleteListener {
                    data.value = Resource.success(true)
                }
                .addOnFailureListener{
                    data.value = Resource.error("Data Gagal Diubah",false)
                }
        } else if(status == 2){
            if (tanggapan.id == null){
                model.status = 2
                model.idTanggapan = idTanggapan

                tanggapan.id = idTanggapan

                database.child(ALL_LAPORAN).child(model.id!!).setValue(model)
                database.child(LAPORAN).child(model.uid!!).child(model.id!!).setValue(model)
                database.child(TANGGAPAN).child(idTanggapan).setValue(tanggapan)
                    .addOnCompleteListener {
                        data.value = Resource.success(true)
                    }
                    .addOnFailureListener {
                        data.value = Resource.error("Gagal!",false)
                    }
            }else{
                database.child(ALL_LAPORAN).child(model.id!!).setValue(model)
                database.child(LAPORAN).child(model.uid!!).child(model.id!!).setValue(model)
                database.child(TANGGAPAN).child(tanggapan.id!!).setValue(tanggapan)
                    .addOnCompleteListener {
                        data.value = Resource.success(true)
                    }
                    .addOnFailureListener {
                        data.value = Resource.error("Gagal!",false)
                    }
            }
        }
        return data
    }

    fun getAllLaporan(): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(ALL_LAPORAN)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()
        data.value = Resource.loading(null)

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
                    data.value = Resource.error("Data Tidak Ada!", null)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                data.value = Resource.error("Gagal!", null)
            }
        })
        return data
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

    fun getDataBelumDiproses(uid: String): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(LAPORAN).child(uid).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    if (i.child(STATUS).value.toString().toInt() == 0) {
                        val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
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

    fun getDataBelumDiproses(): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(ALL_LAPORAN).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    if (i.child(STATUS).value.toString().toInt() == 0) {
                        val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
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

    fun getDataDiproses(uid: String): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(LAPORAN).child(uid).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    if (i.child(STATUS).value.toString().toInt() == 1) {
                        val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
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

    fun getDataDiproses(): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(ALL_LAPORAN).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    if (i.child(STATUS).value.toString().toInt() == 1) {
                        val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
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

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    if (i.child(STATUS).value.toString().toInt() == 2) {
                        val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
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

    fun getDataSelesai(): LiveData<Resource<ArrayList<LaporkanModel>>> {
        val ref = database.child(ALL_LAPORAN).orderByChild(STATUS)
        val listData = ArrayList<LaporkanModel>()
        val data = MutableLiveData<Resource<ArrayList<LaporkanModel>>>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (listData.size > 0) listData.clear()

                for (i in p0.children) {
                    Log.d("ULANG","${i.child(STATUS).value}")
                    val status:Int = i.child(STATUS).value.toString().toInt()
                    if (status == 2) {
                        val laporkanModel: LaporkanModel? = i.getValue(LaporkanModel::class.java)
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