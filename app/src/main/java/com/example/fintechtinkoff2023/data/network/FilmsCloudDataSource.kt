package com.example.fintechtinkoff2023.data.network

import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase

interface FilmsCloudDataSource {
    suspend fun fetchTopMovie() : List<FilmBase>
    class Base(private val kinoPoiskApi: KinoPoiskApi) : FilmsCloudDataSource{
        override suspend fun fetchTopMovie(): List<FilmBase> {
            return kinoPoiskApi.getTopFilms().topFilms.map {
                it.map(Film.Mapper.ToDomain())
            }
        }
    }
}