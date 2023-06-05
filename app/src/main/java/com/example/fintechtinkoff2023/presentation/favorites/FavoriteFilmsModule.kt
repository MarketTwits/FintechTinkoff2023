package com.example.fintechtinkoff2023.presentation.favorites

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.presentation.base.module.BaseModule

class FavoriteFilmsModule(private val core: Core) : Module<FavoritesFilmViewModel> {
    override fun viewModel() = FavoritesFilmViewModel(
        DispatchersList.Base(),
        FavoritesCommunication.Base(),
        BaseModule.Base(core).provideInteractor()
    )
}