package com.example.fintechtinkoff2023.domain.model

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.presentation.filmInfo.HandleInfoUiState

sealed class FilmInfoUi(
    val filmId: Int = 0,
    val name: String = "",
    val posterUrl: String = "",
    val description: String = "",
    val country: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    ) {
    abstract fun handle(handleUi : HandleInfoUiState)
    open fun getMessage(): String = ""
    class Base(
        filmId: Int,
        nameRu: String,
        posterUrl: String,
        description: String,
        county: List<Country>,
        genres: List<Genre>,
    ) : FilmInfoUi(filmId, nameRu, posterUrl, description, county, genres) {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleSuccess(this)
        }
    }
    class Failed(private val text: String) : FilmInfoUi() {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleError(text)
        }
        override fun getMessage() = text
        class FilmNotFound : FilmInfoUi() {
            override fun handle(handleUi: HandleInfoUiState) {
                handleUi.handleError("Not found")//todo
            }
        }
    }

    object Progress : FilmInfoUi() {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleLoading()
        }
    }
}