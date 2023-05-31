package com.example.fintechtinkoff2023.presentation.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFilmsViewModel(
    private val filmsInteractor: FilmInteract,
) : ViewModel() {

    private val _topFilms: MutableLiveData<List<FilmUi>> = MutableLiveData()
    val topFilms: LiveData<List<FilmUi>> = _topFilms

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