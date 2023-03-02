package com.example.fintechtinkoff2023.data.network.retrofit

import com.example.fintechtinkoff2023.data.network.model.PageFilm
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface KinoPoiskApi {


    @Headers("X-API-KEY: $API_KEY")
    @GET("films/top")
     suspend fun getTopFilms(
        @Query(QUERY_PARAM_TYPE) type: String = "TOP_100_POPULAR_FILMS")
    : PageFilm

    companion object {
        private const val QUERY_PARAM_TYPE = "type"
        private const val API_KEY = "fe86bf2b-79bf-4c49-b04a-b6bd30241f6c"
    }

}