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

class FavoritesFilmViewModel(
    private val filmsRepositoryImpl: FilmsRepositoryImpl,
) : ViewModel() {

    init {
        getData()
    }

     val liveData = MutableLiveData<List<FilmUi>>()

    fun getData()  {
        viewModelScope.launch {
            filmsRepositoryImpl.getFavoriteFilms().collect{
                val uiFilms = arrayListOf<FilmUi>()
                it.forEach { filmUi ->
                    uiFilms.add(filmUi.map(Film.Mapper.ToUi()))
                }
                liveData.value = uiFilms
            }
        }
    }
    fun addFilm(filmUi: FilmUi){
        viewModelScope.launch(Dispatchers.IO) {
            val base = FilmBase(filmUi.filmId, filmUi.nameRu, filmUi.posterUrl, filmUi.year)
            filmsRepositoryImpl.addFilmsToFavorite(base)
        }
    }
}