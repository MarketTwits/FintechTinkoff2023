package com.example.fintechtinkoff2023.presentation.screens.filmInfo

import androidx.viewbinding.ViewBinding
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi

interface HandleInfoUiState {
    fun handleSuccess(
        screen : FilmInfoScreen
    )
    fun handleError(text: String)
    fun handleLoading()
    fun changeState(binding: ViewBinding)
}
data class FilmInfoScreen(
     val name: String,
     val posterUrl: String,
     val description: String,
     val country: List<Country>,
     val genres: List<Genre>,
)