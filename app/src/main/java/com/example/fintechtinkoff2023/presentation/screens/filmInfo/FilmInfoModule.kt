package com.example.fintechtinkoff2023.presentation.screens.filmInfo

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.screens.base.module.BaseModule

class FilmInfoModule(private val core: Core) : Module<FilmInfoViewModel> {
    override fun viewModel() = FilmInfoViewModel(
        DispatchersList.Base(),
        core.filmInfoCommunication(),
        FilmIdCommunication.Base(),
        BaseModule.Base(core).provideInteractor()
    )
}