package com.example.fintechtinkoff2023.presentation.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilm
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FilmInfoViewModel : ViewModel() {
    private val filmsRepository = FilmsRepositoryImpl()

    private val _infoFilms: MutableLiveData<NetworkResult<InfoFilm>> = MutableLiveData()
    val infoFilms: LiveData<NetworkResult<InfoFilm>> = _infoFilms

    fun loadTopFilms(filmId: Int) {
        filmsRepository.getInfoAboutFilm(filmId = filmId)
        filmsRepository.infoFilm
            .onStart {
                _infoFilms.value = (NetworkResult.Loading())
            }
            .onEach {
                _infoFilms.value = when (it) {
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