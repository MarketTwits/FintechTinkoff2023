package com.example.fintechtinkoff2023.presentation.favorites

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module

class FavoriteFilmsModule(private val core: Core) : Module<FavoritesFilmViewModel> {
    override fun viewModel() = FavoritesFilmViewModel(
        core.filmsRepository()
    )
}