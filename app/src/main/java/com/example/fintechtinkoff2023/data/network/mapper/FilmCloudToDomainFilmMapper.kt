package com.example.fintechtinkoff2023.data.network.mapper

import com.example.fintechtinkoff2023.domain.model.Film

interface FilmCloudToDomainFilmMapper {
    suspend fun map(film: Film)
    class Base : FilmCloudToDomainFilmMapper{
        override suspend fun map(film: Film) {
            film.map(mapper = Film.Mapper.ToDomain())
        }
    }
}