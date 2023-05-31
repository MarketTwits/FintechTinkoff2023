package com.example.fintechtinkoff2023.presentation.favorites

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.data.database.CacheDataSource

class FavoriteFilmsModule(private val core: Core) : Module<FavoritesFilmViewModel> {
    val cacheDataSource = CacheDataSource.Base(core.database().filmDao())
    override fun viewModel() = FavoritesFilmViewModel(
        core.filmsInteractor()
    )
}