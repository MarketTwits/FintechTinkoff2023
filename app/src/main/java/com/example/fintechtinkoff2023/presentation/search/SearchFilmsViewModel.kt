package com.example.fintechtinkoff2023.presentation.search


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFilmsViewModel(
    private val dispatchersList: DispatchersList,
    private val communication: SearchFilmCommunication,
    private val communicationString: CheckStringCommunication,
    private val filmsInteractor: FilmInteract,
) : ViewModel(), Communication.Observe<List<FilmUi>> {

    private fun load(keywords: String) {
        viewModelScope.launch(dispatchersList.io()) {
            filmsInteractor.fetchSearchFilms(keywords)
                .collectLatest {
                    withContext(dispatchersList.main()) {
                        communication.map(it)
                    }
                }
        }
    }

    fun listen(text: String) {
        if (text != communicationString.fetch()) {
            communicationString.map(text)
            load(text)
        }
    }

    fun itemToCache(item: FilmUi) {
        viewModelScope.launch(Dispatchers.IO) {
            filmsInteractor.addOrRemoveFilm(item)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<FilmUi>>) {
        communication.observe(owner, observer)
    }
}