package com.example.fintechtinkoff2023.presentation.search

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.base.module.BaseModule


class SearchModule(private val core: Core) : Module<SearchFilmsViewModel> {
    override fun viewModel() = SearchFilmsViewModel(
        DispatchersList.Base(),
        SearchFilmCommunication.Base(),
        CheckStringCommunication.Base(),
        BaseModule.Base(core).provideInteractor()
    )
}