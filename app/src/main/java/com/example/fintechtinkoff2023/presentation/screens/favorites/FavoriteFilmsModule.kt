package com.example.fintechtinkoff2023.presentation.screens.favorites

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.screens.base.module.BaseModule

class FavoriteFilmsModule(private val core: Core) : Module<FavoritesFilmViewModel> {
    override fun viewModel() = FavoritesFilmViewModel(
        DispatchersList.Base(),
        FavoritesCommunication.Base(),
        BaseModule.Base(core).provideInteractor()
    )
}