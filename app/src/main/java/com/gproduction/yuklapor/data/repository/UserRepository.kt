package com.gproduction.yuklapor.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.gproduction.yuklapor.data.Resource
import com.gproduction.yuklapor.data.model.UserModel
import com.gproduction.yuklapor.tools.NIK
import com.gproduction.yuklapor.tools.NOHP
import com.gproduction.yuklapor.tools.USERNAME
import com.gproduction.yuklapor.tools.USERS

class UserRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference


    fun userLogin(email: String, password: String): LiveData<Resource<AuthResult>> {

        val loginResult = MutableLiveData<Resource<AuthResult>>()

        mAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    loginResult.value = Resource.success(it.result)
                } else{
                    loginResult.value = Resource.error("Username atau Password Salah!", null)

                }
            }

        return loginResult
    }

    fun userRegister(email: String, password: String): LiveData<Resource<AuthResult>> {
        val authResult = MutableLiveData<Resource<AuthResult>>()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    authResult.value = Resource.success(it.result)
                } else {
                    authResult.value = Resource.errorThrowable(it.exception,null)
                }
            }
        return authResult
    }

    fun registerToDatabase(uid: String,nik:String,nama: String, email: String?, username:String,telepon:String) {
        val userModel = UserModel(
            nik,
            nama,
            email,
            username,
            telepon
        )

        database.child(USERS).child(uid).setValue(userModel)
    }

    fun checkDuplicateData(nik: String,username: String,noHp:String): LiveData<Resource<Boolean>>{
        val boolean = MutableLiveData<Resource<Boolean>>()
        database.child(USERS).orderByChild("nik").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                var checked = true
                for(snapshot in p0.children){
                    snapshot.child(NIK).value?.let {
                        if (it == nik){
                            boolean.value = Resource.error("NIK Sudah Ada!",false)
                            checked = false
                            return
                        }
                    }
                    snapshot.child(USERNAME).value?.let {
                        if (it == username){
                            boolean.value = Resource.error("Username sudah ada!", false)
                            checked = false
                            return
                        }
                    }
                    snapshot.child(NOHP).value?.let {
                        if (it == noHp){
                            boolean.value = Resource.error("Nomor Telepon sudah ada!", false)
                            checked = false
                            return
                        }
                    }
                }
                if (checked){
                    boolean.value = Resource.success(true)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                boolean.value = Resource.error("Gagal!",false)
                return
            }
        })

        return boolean
    }

     fun getUserData(uid:String) : LiveData<UserModel>{
        val userData = MutableLiveData<UserModel>()
        database.child(USERS).child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                userData.value = p0.getValue(UserModel::class.java)
            }
            override fun onCancelled(p0: DatabaseError) {
                userData.value = null
            }
        })
        return userData
    }

    fun getNIK(uid:String) : LiveData<String>{
        val nik = MutableLiveData<String>()
        database.child(USERS).child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                nik.value = p0.child("nik").value.toString()
            }
            override fun onCancelled(p0: DatabaseError) {
                nik.value = null
            }
        })
        return nik
    }

}