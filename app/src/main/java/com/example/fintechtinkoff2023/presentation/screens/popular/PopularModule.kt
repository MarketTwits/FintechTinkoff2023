package com.example.fintechtinkoff2023.presentation.screens.popular

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.screens.base.module.BaseModule

class PopularModule(
    private val core: Core,
) : Module<PopularFilmsViewModel> {
    override fun viewModel(): PopularFilmsViewModel {
        return PopularFilmsViewModel(
            DispatchersList.Base(),
            PopularFilmCommunication.Base(),
            core.providePeriodicInteractor()
        )
    }
}