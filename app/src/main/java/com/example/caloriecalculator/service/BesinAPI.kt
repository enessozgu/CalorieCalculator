package com.example.caloriecalculator.service

import com.example.caloriecalculator.model.Besin
import retrofit2.http.GET

interface BesinAPI {

    // BASE URL -> https://raw.githubusercontent.com/
    // ENDPOINT -> atilsamancioglu/BTK20-JSONVeriSeti/refs/heads/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/refs/heads/master/besinler.json")

   suspend fun getBesin():List<Besin>

}

