package com.example.fintechtinkoff2023.presentation.popular


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PopularFilmsViewModel(
    private val filmsInteractor: FilmInteract,
) : ViewModel() {
    private val _topFilms: MutableLiveData<List<FilmUi>> = MutableLiveData()
    val topFilms: LiveData<List<FilmUi>> = _topFilms

    init {
        fetchTopFilms()
    }

    fun fetchTopFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            filmsInteractor.fetchTopFilms()
            filmsInteractor.topFilms.collect {
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