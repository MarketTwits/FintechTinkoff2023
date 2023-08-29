package com.example.fintechtinkoff2023.presentation.screens.filmInfo.loading

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module

class FilmInfoLoadingModule(private val core: Core) : Module<FilmInfoLoadingViewModel> {
    override fun viewModel(): FilmInfoLoadingViewModel = FilmInfoLoadingViewModel(
        core.filmInfoCommunication()
    )
}