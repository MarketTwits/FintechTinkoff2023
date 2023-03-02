package com.example.fintechtinkoff2023.presentation.popular


import androidx.lifecycle.*
import com.example.fintechtinkoff2023.data.network.model.PageFilm
import com.example.fintechtinkoff2023.data.repository.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.state.NetworkResult

import kotlinx.coroutines.flow.*


class PopularFilmsViewModel : ViewModel() {

    private val filmsRepository = FilmsRepositoryImpl()

    private val _topFilms: MutableLiveData<NetworkResult<PageFilm>> = MutableLiveData()
    val topFilms: LiveData<NetworkResult<PageFilm>> = _topFilms

    init {
        loadTopFilms()
    }

    private fun loadTopFilms() {
        filmsRepository.getTopMovie()
        filmsRepository.flow
            .onStart {
                _topFilms.value = (NetworkResult.Loading())
            }
            .onEach {
                _topFilms.value = when(it) {
                    is NetworkResult.Error->
                        NetworkResult.Error(it.message)
                    is NetworkResult.Success ->{
                        NetworkResult.Success(it.data!!)
                    }
                    else ->  NetworkResult.Error("Unknown error")
                }
            }
            .launchIn(viewModelScope)
    }

}