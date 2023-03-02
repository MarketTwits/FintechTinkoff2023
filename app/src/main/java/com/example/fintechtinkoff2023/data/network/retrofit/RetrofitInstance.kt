package com.example.fintechtinkoff2023.data.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

    val kinoPoiskApiInstance = getInstance().create(KinoPoiskApi::class.java)

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}