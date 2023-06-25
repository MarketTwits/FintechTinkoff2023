package com.example.fintechtinkoff2023.presentation.screens.popular


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PopularFilmsViewModel(
    private val dispatchersList: DispatchersList,
    private val communication: PopularFilmCommunication,
    private val filmsInteractor: FilmInteract,
) : ViewModel(), Communication.Observe<List<FilmUi>> {

    init {
        fetchTopFilms()
    }
    fun fetchTopFilms() {
            viewModelScope.launch(dispatchersList.io()) {
                filmsInteractor.fetchTopFilms().collect {
                    withContext(dispatchersList.main()) {
                        communication.map(it)
                    }
                }
            }
    }

    fun itemToCache(item: FilmUi) {
        viewModelScope.launch(dispatchersList.io()) {
            filmsInteractor.addOrRemoveFilm(item)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<FilmUi>>) {
        communication.observe(owner, observer)
    }
}