package com.example.fintechtinkoff2023.data.network

import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilm
import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi


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


