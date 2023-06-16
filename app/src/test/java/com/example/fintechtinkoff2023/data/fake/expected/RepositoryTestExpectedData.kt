package com.example.fintechtinkoff2023.data.fake.expected

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase

interface RepositoryTestExpectedData {
    fun fetchFilmsSuccessData(): List<FilmBase>
    fun fetchInfoAboutFilmSuccessData(): FilmInfoBase
    fun fetchFavoritesFilmsSuccess() : List<FilmBase>
    fun fetchFavoritesFilmsEmpty() : List<FilmBase>
    fun fetchBaseFilm() : FilmBase
    class Base : RepositoryTestExpectedData {
        override fun fetchFilmsSuccessData(): List<FilmBase> {
            val filmBase1 = FilmBase(
                filmId = 1,
                name = "Фильм1",
                posterUrl = "https://example.com/poster1_preview.jpg",
                year = "2021"
            )
            val filmBase2 = FilmBase(
                filmId = 2,
                name = "Фильм2",
                posterUrl = "https://example.com/poster2_preview.jpg",
                year = "2022"
            )
            return listOf(filmBase1, filmBase2)
        }
        override fun fetchInfoAboutFilmSuccessData(): FilmInfoBase {
            return FilmInfoBase(
                country = listOf(Country("Country1")),
                filmId = 1, genres = listOf(Genre("Genre1")),
                name = "Фильм1", posterUrl = "https://example.com/poster1.jpg",
                description = "Film 1 description", year = "2021"
            )
        }
        override fun fetchFavoritesFilmsSuccess(): List<FilmBase> {
            val filmBase1 = FilmBase(
                filmId = 1,
                name = "Фильм1",
                posterUrl = "https://example.com/poster1.jpg",
                year = "2021"
            )
            val filmBase2 = FilmBase(
                filmId = 2,
                name = "Фильм2",
                posterUrl = "https://example.com/poster2.jpg",
                year = "2022"
            )
            return listOf(filmBase1, filmBase2)
        }

        override fun fetchFavoritesFilmsEmpty(): List<FilmBase> {
            return emptyList()
        }

        override fun fetchBaseFilm(): FilmBase {
            return FilmBase(
                filmId = 1,
                name = "Фильм1",
                posterUrl = "https://example.com/poster1.jpg",
                year = "2021"
            )
        }
    }
}