package com.example.fintechtinkoff2023.presentation.models

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.HandleInfoUiState


interface FilmInfoUi {
    fun handle(handleUi: HandleInfoUiState)
    class Base(
       private val filmId: Int,
       private val nameRu: String,
       private val posterUrl: String,
       private val description: String,
       private val county: List<Country>,
       private val genres: List<Genre>,
    ) : com.example.fintechtinkoff2023.presentation.models.FilmInfoUi {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleSuccess(FilmInfoUi( nameRu, posterUrl, description, county, genres))
        }
    }
    class Failed(private val text: String) :
        com.example.fintechtinkoff2023.presentation.models.FilmInfoUi {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleError(text)
        }
    }
    class NotFound(private val text: String) :
        com.example.fintechtinkoff2023.presentation.models.FilmInfoUi {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleError(text)
        }
    }
    object Progress :
        com.example.fintechtinkoff2023.presentation.models.FilmInfoUi {
        override fun handle(handleUi: HandleInfoUiState) {
            handleUi.handleLoading()
        }
    }
}
