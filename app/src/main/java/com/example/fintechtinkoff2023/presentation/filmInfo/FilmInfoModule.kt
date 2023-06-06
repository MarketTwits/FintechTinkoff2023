package com.example.fintechtinkoff2023.presentation.filmInfo

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.base.module.BaseModule

class FilmInfoModule(private val core: Core) : Module<FilmInfoViewModel> {
    override fun viewModel() = FilmInfoViewModel(
        DispatchersList.Base(),
        FilmInfoCommunication.Base(),
        FilmIdCommunication.Base(),
        BaseModule.Base(core).provideInteractor()
    )
}