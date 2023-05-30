package com.example.fintechtinkoff2023.data.network.mapper

import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase

interface FilmsCloudToDomainFilmMapper {

    suspend fun map(films: List<Film>): List<FilmBase>
    class Base : FilmsCloudToDomainFilmMapper {
        override suspend fun map(films: List<Film>): List<FilmBase> {
            return films.map {
                it.map(Film.Mapper.ToDomain())
            }
        }
    }
}