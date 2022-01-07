package br.com.helpdev.kycform.helper

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE


 class PrefrenceManger1 {
    lateinit var sharedPreferences: SharedPreferences

    fun setMobileno(mobile: String?, context: Context) {
        sharedPreferences = context.getSharedPreferences("zxcvbnm", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("mobile", mobile)
        editor.apply()

    }

    fun getmobilno(context: Context): String? {
        sharedPreferences = context.getSharedPreferences("zxcvbnm", MODE_PRIVATE)
        return sharedPreferences.getString("mobile", "0")
    }

    fun getName(context: Context): String? {
        sharedPreferences = context.getSharedPreferences("zxcvbnm", MODE_PRIVATE)
        return sharedPreferences.getString("name", "0")
    }

     fun setName(name: String?, context: Context) {
        sharedPreferences = context.getSharedPreferences("zxcvbnm", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()

    }

     fun setDob(dob: String?, context: Context) {
         sharedPreferences = context.getSharedPreferences("zxcvbnm", MODE_PRIVATE)
         val editor: SharedPreferences.Editor = sharedPreferences.edit()
         editor.putString("dob", dob)
         editor.apply()

     }


     fun getDob(context: Context): String? {
        sharedPreferences = context.getSharedPreferences("zxcvbnm", MODE_PRIVATE)
        return sharedPreferences.getString("dob", "0")
    }

}