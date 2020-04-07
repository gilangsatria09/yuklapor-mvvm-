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
}