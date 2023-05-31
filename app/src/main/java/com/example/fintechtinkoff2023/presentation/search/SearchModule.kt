package com.example.fintechtinkoff2023.presentation.search

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module


class SearchModule(private val core: Core) : Module<SearchFilmsViewModel> {
    override fun viewModel() = SearchFilmsViewModel(
        core.filmsInteractor()
    )
}