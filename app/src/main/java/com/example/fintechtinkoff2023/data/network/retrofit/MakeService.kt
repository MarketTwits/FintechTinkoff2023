package com.example.fintechtinkoff2023.data.network.retrofit

import com.example.fintechtinkoff2023.FintechApp
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

interface MakeService {
    fun client(): OkHttpClient
    fun service(): KinoPoiskApi
    class Base() : MakeService {
        override fun client() = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

        override fun service() =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client())
                .build()
                .create(KinoPoiskApi::class.java)
    }

    private companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"
    }

}