package com.example.fintechtinkoff2023.presentation.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFilmViewModel(
    private val filmsRepositoryImpl: FilmsRepositoryImpl,
) : ViewModel() {
    init {
        getData()
    }
    val liveData = MutableLiveData<List<FilmUi>>()
    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            filmsRepositoryImpl.getFavoriteFilms()
            filmsRepositoryImpl.favoritesFilms.collect {
                withContext(Dispatchers.Main) {
                    liveData.value = it
                }
            }
        }
    }
    fun addFilm(filmUi: FilmUi) {
        viewModelScope.launch(Dispatchers.IO) {
            val base = FilmBase(filmUi.filmId, filmUi.nameRu, filmUi.posterUrl, filmUi.year)
            filmsRepositoryImpl.addFilmsToFavorite(base)
        }
    }
}