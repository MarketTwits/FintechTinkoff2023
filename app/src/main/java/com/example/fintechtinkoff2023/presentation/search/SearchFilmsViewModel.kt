package com.example.fintechtinkoff2023.presentation.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.data.network.FilmInteractor
import com.example.fintechtinkoff2023.data.network.TestRepository
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFilmsViewModel(
    private val filmsInteractor: FilmInteractor,
    private val filmsRepository: FilmsRepositoryImpl,
) : ViewModel() {

    private val _topFilms: MutableLiveData<List<FilmUi>> = MutableLiveData()
    val topFilms: LiveData<List<FilmUi>> = _topFilms

    fun loadTopFilms(keywords: String) {
        viewModelScope.launch {
            filmsRepository.getSearchMovie(keywords = keywords)
            filmsRepository.searchFilms
                .onStart {
                    _topFilms.value = arrayListOf(FilmUi.Progress)
                }
                .onEach {
                    _topFilms.value = when (it) {
                        is NetworkResult.Error -> arrayListOf(FilmUi.Failed(it.message))
                        is NetworkResult.Error.NotFound -> arrayListOf(FilmUi.Failed.FilmNotFound())
                        is NetworkResult.Success -> it.data
                        is NetworkResult.Loading -> arrayListOf(FilmUi.Progress)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun load(keywords: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filmsInteractor.fetchSearchFilms(keywords)
            filmsInteractor.topFilms
                .collect {
                    withContext(Dispatchers.Main) {
                        _topFilms.value = it
                    }
                }
        }
    }

    fun itemToCache(item: FilmUi) {
        viewModelScope.launch(Dispatchers.IO) {
            filmsInteractor.addOrRemoveFilm(item)
        }
    }
}