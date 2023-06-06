package com.example.fintechtinkoff2023.presentation.filmInfo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmInfoViewModel
    (
    private val dispatchersList: DispatchersList,
    private val communication : FilmInfoCommunication,
    private val interactor: FilmInteract
) : ViewModel(), Communication.Observe<FilmInfoUi> {
    fun loadInfoAboutFilm(filmId: Int) {
        viewModelScope.launch(dispatchersList.io()) {
            interactor.fetchInfoFilm(filmId).collect {
                withContext(dispatchersList.main()) {
                    communication.map(it)
                }
            }
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<FilmInfoUi>) {
        communication.observe(owner, observer)
    }
}