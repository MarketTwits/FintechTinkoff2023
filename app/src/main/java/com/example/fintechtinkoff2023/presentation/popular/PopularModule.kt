package com.example.fintechtinkoff2023.presentation.popular

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module

class PopularModule(
    private val core: Core,
) : Module<PopularFilmsViewModel> {
    override fun viewModel(): PopularFilmsViewModel {
         return PopularFilmsViewModel(
            core.filmsInteractor()
        )
    }
}