package com.example.fintechtinkoff2023.data.network.retrofit

import com.example.fintechtinkoff2023.data.network.models.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.data.network.models.page_film.TopFilmPage
import com.example.fintechtinkoff2023.data.network.models.search_films.SearchFilmsPage


import retrofit2.http.*

interface KinoPoiskApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/top")
    suspend fun getTopFilms(
        @Query(QUERY_PARAM_TYPE) type: String = "TOP_100_POPULAR_FILMS",
        @Query(QUERY_PARAM_PAGE) page: Int = 1
    ): TopFilmPage

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.1/films/search-by-keyword")
    suspend fun getFilmsByKeyWords(
        @Query(QUERY_PARAM_KEYWORD) keyword: String = "",
        @Query(QUERY_PARAM_PAGE) page: Int = 1,
    ): SearchFilmsPage

    @Headers("X-API-KEY: $API_KEY")
    @GET("v2.2/films/{filmId}")
    suspend fun getInfoAboutFilmById(
        @Path("filmId") filmId: Int
    ) : InfoFilmCloud

    private companion object {
        private const val QUERY_PARAM_TYPE = "type"
        private const val QUERY_PARAM_KEYWORD = "keyword"
        private const val QUERY_PARAM_PAGE = "page"
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
        //500 per day --> "fe86bf2b-79bf-4c49-b04a-b6bd30241f6c"
        //20 sec --> e30ffed0-76ab-4dd6-b41f-4c9da2b2735b
    }

}