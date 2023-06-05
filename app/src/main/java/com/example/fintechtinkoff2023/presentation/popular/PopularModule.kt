package com.example.fintechtinkoff2023.presentation.popular

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.base.module.BaseModule
import com.example.fintechtinkoff2023.presentation.film.FilmInfoCommunication

class PopularModule(
    private val core: Core,
) : Module<PopularFilmsViewModel> {
    override fun viewModel(): PopularFilmsViewModel {
        return PopularFilmsViewModel(
            DispatchersList.Base(),
            PopularFilmCommunication.Base(),
            BaseModule.Base(core).provideInteractor()
        )
    }
}