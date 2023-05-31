package com.example.fintechtinkoff2023.data.network

import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilm
import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import java.lang.Exception


interface FilmsCloudDataSource {
    suspend fun fetchTopMovie(): List<TopFilm>
    suspend fun fetchSearchMovie(text: String): List<SearchFilm>
    suspend fun fetchInfoAboutFilm(filmId: Int): InfoFilmCloud
    class Base(private val kinoPoiskApi: KinoPoiskApi) : FilmsCloudDataSource {
        override suspend fun fetchTopMovie() = kinoPoiskApi.getTopFilms().topFilms
        override suspend fun fetchSearchMovie(text: String) = kinoPoiskApi.getFilmsByKeyWords(text).searchFilms
        override suspend fun fetchInfoAboutFilm(filmId: Int) = kinoPoiskApi.getInfoAboutFilmById(filmId)
    }
}
//todo
interface TestCloudDataSource {
    suspend fun fetchTopMovie(): NetworkResult<List<TopFilm>>

    class Base(
        private val errorTypeDomainMapper: ErrorTypeDomainMapper,
        private val kinoPoiskApi: KinoPoiskApi) : TestCloudDataSource {
        override suspend fun fetchTopMovie() :  NetworkResult<List<TopFilm>>  {
            try {
                val data = kinoPoiskApi.getTopFilms().topFilms
                if (data.isEmpty()) {
                    return NetworkResult.Error.NotFound()
                }
                return NetworkResult.Success(data)
            } catch (e: Exception) {
                return NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }
    }
}


