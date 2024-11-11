package com.example.caloriecalculator.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.caloriecalculator.roomdb.BesinDatabase

class OzelSharedPreferences {


    companion object {

        private val TIME="time"

        private var sharedPreferences:SharedPreferences?=null

        @Volatile
        private var instance: OzelSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: OzelSharedPreferencesOlustur(context).also {
                instance = it
            }
        }


        private fun OzelSharedPreferencesOlustur(context: Context):OzelSharedPreferences{
            sharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()
        }



    }


    fun zamaniKaydet(zaman:Long){
        sharedPreferences?.edit()?.putLong(TIME,zaman)?.apply()
    }

    fun zamaniAl()= sharedPreferences?.getLong(TIME,0)


}