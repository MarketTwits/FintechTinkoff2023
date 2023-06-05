package com.example.fintechtinkoff2023.domain.model

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre

sealed class FilmInfoUi(
    val filmId: Int = 0,
    val name: String = "",
    val posterUrl: String = "",
    val description: String = "",
    val country: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    ) {
    open fun getMessage(): String = ""
    class Base(
        filmId: Int,
        nameRu: String,
        posterUrl: String,
        description: String,
        county: List<Country>,
        genres: List<Genre>,
    ) : FilmInfoUi(filmId, nameRu, posterUrl, description, county, genres)

    class Failed(private val text: String) : FilmInfoUi() {
        override fun getMessage() = text
        class FilmNotFound : FilmInfoUi()
    }

    object Progress : FilmInfoUi()
}