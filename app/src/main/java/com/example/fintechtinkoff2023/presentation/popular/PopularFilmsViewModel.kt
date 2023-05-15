package com.example.fintechtinkoff2023.presentation.popular


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class PopularFilmsViewModel(
    private val filmsRepository: FilmsRepositoryImpl,
) : ViewModel() {

    private val _topFilms: MutableLiveData<List<FilmUi>> = MutableLiveData()
    val topFilms: LiveData<List<FilmUi>> = _topFilms

    init {
        loadTopFilms()
    }

        fun loadTopFilms() {
        viewModelScope.launch {
            filmsRepository.getTopMovie()
            filmsRepository.topFilms
                .onStart {
                    _topFilms.value = arrayListOf(FilmUi.Progress)
                }
                .onEach {
                    _topFilms.value = when (it) {
                        is NetworkResult.Error -> arrayListOf(FilmUi.Failed(it.message!!))
                        is NetworkResult.Success -> it.data
                        else -> arrayListOf(FilmUi.Failed(it.message!!))
                    }
                }
                .launchIn(viewModelScope)
        }

    }

    fun itemToCache(item: FilmUi) {
        val baseFilm = FilmBase(item.filmId, item.nameRu, item.posterUrl, item.year)
        viewModelScope.launch(Dispatchers.IO) {
            filmsRepository.addFilmsToFavorite(baseFilm)
        }
    }

}