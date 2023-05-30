package com.example.fintechtinkoff2023.presentation.film

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module

class FilmInfoModule(private val core: Core) : Module<FilmInfoViewModel> {
    override fun viewModel() = FilmInfoViewModel(
        core.filmsRepository()
    )
}