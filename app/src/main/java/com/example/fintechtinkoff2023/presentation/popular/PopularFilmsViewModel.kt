package com.example.fintechtinkoff2023.presentation.popular


import androidx.lifecycle.*
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilmPage
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.state.NetworkResult

import kotlinx.coroutines.flow.*


class PopularFilmsViewModel(
    ) : ViewModel() {

    private val filmsRepository = FilmsRepositoryImpl()

    private val _topFilms: MutableLiveData<NetworkResult<TopFilmPage>> = MutableLiveData()
    val topFilms: LiveData<NetworkResult<TopFilmPage>> = _topFilms

    init {
        loadTopFilms()
    }

    fun loadTopFilms() {
        filmsRepository.getTopMovie()
        filmsRepository.topFilms
            .onStart {
                _topFilms.value = (NetworkResult.Loading())
            }
            .onEach {
                _topFilms.value = when (it) {
                    is NetworkResult.Error ->
                        NetworkResult.Error(it.message)
                    is NetworkResult.Success -> {
                        NetworkResult.Success(checkNotNull(it.data))
                    }
                    else -> NetworkResult.Error("Unknown error")
                }
            }
            .launchIn(viewModelScope)
    }

}