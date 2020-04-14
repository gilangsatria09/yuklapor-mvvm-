package com.gproduction.yuklapor.tools

import android.content.Context

class SharedPreferences(context: Context){
    private val sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCESNAME,0)
    private val editor = sharedPreferences.edit()


    fun setUid(uid:String?){
        editor.putString(SHARED_UID,uid).apply()
    }
    fun getUid() : String?{
        return sharedPreferences.getString(SHARED_UID,null)
    }

    fun setNik(nik:String?){
        editor.putString(SHARED_NIK,nik).apply()
    }
    fun getNik():String?{
        return sharedPreferences.getString(SHARED_NIK,null)
    }

    fun setRole(role:Int){
        editor.putInt(SHARED_ROLE,role).apply()
    }
    fun getRole():Int{
        return sharedPreferences.getInt(SHARED_ROLE,3)
    }

    fun setNama(nama:String?){
        editor.putString(SHARED_NAMA,nama).apply()
    }
    fun getNama():String?{
        return sharedPreferences.getString(SHARED_NAMA,null)
    }
}