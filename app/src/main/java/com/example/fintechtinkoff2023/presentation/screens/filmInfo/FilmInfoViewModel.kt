package com.example.fintechtinkoff2023.presentation.screens.filmInfo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.error.RetryCommunication
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmInfoViewModel(
    private val dispatchersList: DispatchersList,
    private val communication: FilmInfoCommunication,
    private val communicationId: FilmIdCommunication,
    private val retryCommunication: RetryCommunication,
    private val interactor: FilmInteract
) : ViewModel() {
    fun loadInfoAboutFilm(filmId: Int) {
        viewModelScope.launch(dispatchersList.io()) {
            interactor.fetchInfoFilm(filmId).collect {
                withContext(dispatchersList.main()) {
                    communication.map(it)
                }
            }
        }
    }

    fun fetchFilm(filmId: Int) {
        viewModelScope.launch(dispatchersList.main()) {
            communicationId.map(filmId)
        }
    }

    fun observeRetry(owner: LifecycleOwner, observer: Observer<Unit>) {
        retryCommunication.observe(owner, observer)
    }
}