package com.example.fintechtinkoff2023.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilmsPage


import com.example.fintechtinkoff2023.data.repository.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class SearchFilmsViewModel : ViewModel() {
    private val filmsRepository = FilmsRepositoryImpl()

    private val _topFilms: MutableLiveData<NetworkResult<SearchFilmsPage>> = MutableLiveData()
    val topFilms: LiveData<NetworkResult<SearchFilmsPage>> = _topFilms

    fun loadTopFilms(keywords: String) {
        filmsRepository.getSearchMovie(keywords = keywords)
        filmsRepository.searchFilms
            .onStart {
                _topFilms.value = (NetworkResult.Loading())
            }
            .onEach {
                _topFilms.value = when (it) {
                    is NetworkResult.Error ->
                        NetworkResult.Error(it.message)
                    is NetworkResult.Success -> {
                        NetworkResult.Success(it.data!!)
                    }
                    else -> NetworkResult.Error("Unknown error")
                }
            }
            .launchIn(viewModelScope)
    }
}