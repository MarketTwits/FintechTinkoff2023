package com.example.fintechtinkoff2023.presentation.filmInfo

import androidx.viewbinding.ViewBinding
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi

interface HandleInfoUiState {
    fun handleSuccess(film: FilmInfoUi)
    fun handleError(text : String)
    fun handleLoading()
    fun changeState(binding : ViewBinding)
}