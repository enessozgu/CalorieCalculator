package com.example.caloriecalculator.roomdb

import android.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.caloriecalculator.model.Besin


@Dao
interface BesinDAO {



     @Query("SELECT * FROM besin")
     suspend fun getAllBesin():List<Besin>

     @Insert
     suspend fun insertAll(vararg besin: Besin):List<Long>

     @Query("DELETE FROM besin")
     suspend fun deleteAllBesin()

     @Query("SELECT * FROM besin WHERE uuid= :besinId")
     suspend fun getBesin(besinId: Int):Besin


}