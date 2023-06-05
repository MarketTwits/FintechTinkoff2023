package com.example.fintechtinkoff2023.presentation.popular


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.film.FilmInfoCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PopularFilmsViewModel(
    private val dispatchersList: DispatchersList,
    private val communication : PopularFilmCommunication,
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