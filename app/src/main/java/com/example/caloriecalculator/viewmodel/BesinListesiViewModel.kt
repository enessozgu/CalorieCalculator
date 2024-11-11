package com.example.caloriecalculator.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecalculator.model.Besin
import com.example.caloriecalculator.roomdb.BesinDatabase
import com.example.caloriecalculator.service.BesinAPIService
import com.example.caloriecalculator.util.OzelSharedPreferences
import com.example.caloriecalculator.view.BesinListesi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BesinListesiViewModel(application: Application):AndroidViewModel(application) {


    val besinler=MutableLiveData<List<Besin>>()
    val besinHataMesaji=MutableLiveData<Boolean>()
    val besinYukleniyor=MutableLiveData<Boolean>()

    private val besinAPIService=BesinAPIService()
    private val ozelSharedPreferences=OzelSharedPreferences(getApplication())

    private val guncellemeZamani=0.1*60*1000*1000*1000L


     fun refreshData(){
        val kaydedilmeZamani=ozelSharedPreferences.zamaniAl()

        if (kaydedilmeZamani !=null && kaydedilmeZamani !=0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            verileriRoomdanAl()
        }else{
            verileriInternettenAl()
        }
    }


    private fun verileriRoomdanAl(){
        besinYukleniyor.value=true

        viewModelScope.launch {
            val besinListesi=BesinDatabase(getApplication()).besinDao().getAllBesin()
            withContext(Dispatchers.Main){
                besinleriGoster(besinListesi)
                Toast.makeText(getApplication(),"Besinleri Roomdan Aldık",Toast.LENGTH_SHORT)
            }
        }

    }




     fun verileriInternettenAl(){

        besinYukleniyor.value=true

        viewModelScope.launch(Dispatchers.IO) {
            val besinListesi=besinAPIService.getData()
            withContext(Dispatchers.Main){
                besinYukleniyor.value=false
                roomKaydet(besinListesi)
                Toast.makeText(getApplication(),"Verileri İnternetten aldık",Toast.LENGTH_SHORT)
            }
        }

    }


    private fun besinleriGoster(besinListesi: List<Besin>){
        besinler.value=besinListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false
    }


    private fun roomKaydet(besinListesi: List<Besin>){

        viewModelScope.launch {

            val dao=BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi=dao.insertAll(*besinListesi.toTypedArray())

            var i=0
            while (i<besinListesi.size){
                besinListesi[i].uuid=uuidListesi[i].toInt()
                i=i+1
            }

            besinleriGoster(besinListesi)


        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }





}