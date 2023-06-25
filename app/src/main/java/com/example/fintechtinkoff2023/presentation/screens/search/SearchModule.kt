package com.example.fintechtinkoff2023.presentation.screens.search

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.presentation.screens.base.module.BaseModule


class SearchModule(private val core: Core) : Module<SearchFilmsViewModel> {
    override fun viewModel(): SearchFilmsViewModel {
        val dispatchersList = DispatchersList.Base()
        val searchCommunication = SearchFilmCommunication.Base()
        val checkStringCommunication = CheckStringCommunication.Base()
        val interactor = BaseModule.Base(core).provideInteractor()
        return SearchFilmsViewModel(
            SearchFilmsLoader.Base(
                dispatchersList, searchCommunication, interactor
            ),
            searchCommunication,
            checkStringCommunication,
        )
    }

}