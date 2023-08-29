package com.example.fintechtinkoff2023.presentation.screens.filmInfo

import androidx.viewbinding.ViewBinding
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre

interface HandleInfoUiState {
    fun handleSuccess(
        screen : FilmInfoUi
    ) = Unit
    fun handleError(text: String) = Unit
    fun handleLoading() = Unit
}
data class FilmInfoUi(
     val name: String,
     val posterUrl: String,
     val description: String,
     val country: List<Country>,
     val genres: List<Genre>,
)