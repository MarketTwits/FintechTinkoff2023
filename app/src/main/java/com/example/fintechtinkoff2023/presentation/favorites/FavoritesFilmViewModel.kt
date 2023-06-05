package com.example.fintechtinkoff2023.presentation.favorites

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFilmViewModel(
    private val dispatchers : DispatchersList,
    private val communication: FavoritesCommunication,
    private val interactor: FilmInteract,
) : ViewModel(), Communication.Observe<List<FilmUi>> {
    init {
        fetch()
    }
    private fun fetch() {
        viewModelScope.launch(dispatchers.io()) {
            interactor.fetchFavoriteFilms().collect {
                withContext(dispatchers.main()) {
                    communication.map(it)
                }
            }
        }
    }
    fun addFilm(filmUi: FilmUi) {
        viewModelScope.launch(dispatchers.io()) {
            interactor.addOrRemoveFilm(filmUi)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<FilmUi>>) {
        communication.observe(owner, observer)
    }
}