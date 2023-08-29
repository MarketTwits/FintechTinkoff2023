package com.example.fintechtinkoff2023.presentation.screens.filmInfo.error

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module

class FilmInfoErrorModule(private val core: Core) : Module<FilmInfoErrorViewModel> {
    override fun viewModel(): FilmInfoErrorViewModel = FilmInfoErrorViewModel(
        core.filmInfoCommunication()
    )
}