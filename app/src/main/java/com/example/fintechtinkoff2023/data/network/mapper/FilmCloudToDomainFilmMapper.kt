package com.example.fintechtinkoff2023.data.network.mapper

import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase

interface FilmsCloudToDomainFilmMapper {

    suspend fun mapFilms(films: List<Film>): List<FilmBase>
    suspend fun mapFilm(film : FilmInfo) : FilmInfoBase
    class Base : FilmsCloudToDomainFilmMapper {
        override suspend fun mapFilms(films: List<Film>): List<FilmBase> {
            return films.map {
                it.map(Film.Mapper.ToDomain())
            }
        }
        override suspend fun mapFilm(film: FilmInfo): FilmInfoBase {
            return film.map(FilmInfo.Mapper.ToInfoBase())
        }
    }
}