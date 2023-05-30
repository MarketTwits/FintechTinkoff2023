package com.example.fintechtinkoff2023.domain.model

import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache


interface Film {
    suspend fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        suspend fun map(
            filmId: Int,
            nameRu: String,
            posterUrl: String,
            year: String = "",
        ): T
        class ToDomain : Mapper<FilmBase>{
            override suspend fun  map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ) = FilmBase(filmId, nameRu, posterUrl, year)
        }
        class ToCache : Mapper<FilmCache>{
            override suspend fun map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ): FilmCache = FilmCache(filmId = filmId, nameRu = nameRu, posterUrl = posterUrl, year = year)
        }
        class ToFavoriteUi : Mapper<FilmUi>{
            override suspend fun map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ): FilmUi = FilmUi.Favorite(filmId, nameRu, posterUrl, year)
        }
        class ToBaseUi : Mapper<FilmUi>{
            override suspend fun map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ): FilmUi = FilmUi.Base(filmId, nameRu, posterUrl, year)
        }
    }
}
data class FilmBase(
    private val filmId: Int,
    private val name: String,
    private val posterUrl: String,
    private val year: String,
) : Film {
    override suspend fun <T> map(mapper: Film.Mapper<T>): T =
        mapper.map(filmId, name, posterUrl, year)
}
