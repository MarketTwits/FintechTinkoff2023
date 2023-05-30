package com.example.fintechtinkoff2023.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.data.network.FilmInteractor
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFilmViewModel(
    private val filmsRepositoryImpl: FilmsRepositoryImpl,
    private val interactor: FilmInteractor,
) : ViewModel() {
    init {
        //getData()
        fetch()
    }

    private val _favoriteFilms = MutableLiveData<List<FilmUi>>()
    val favoriteFilms: LiveData<List<FilmUi>> = _favoriteFilms
    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            filmsRepositoryImpl.getFavoriteFilms()
            filmsRepositoryImpl.favoritesFilms.collect {
                withContext(Dispatchers.Main) {
                    _favoriteFilms.value = it
                }
            }
        }
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.fetchFavoriteFilms()
            interactor.favoriteFilms.collect {
                withContext(Dispatchers.Main) {
                    _favoriteFilms.value = it
                }
            }
        }
    }

    fun addFilm(filmUi: FilmUi) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.addOrRemoveFilm(filmUi)
        }
    }
}