package com.example.fintechtinkoff2023.presentation.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FilmInfoViewModel(private val filmsRepository : FilmsRepositoryImpl) : ViewModel() {

    private val _infoFilmsCloud: MutableLiveData<NetworkResult<InfoFilmCloud>> = MutableLiveData()
    val infoFilmsCloud: LiveData<NetworkResult<InfoFilmCloud>> = _infoFilmsCloud

    fun loadTopFilms(filmId: Int) {
        filmsRepository.getInfoAboutFilm(filmId = filmId)
        filmsRepository.infoFilmCloud
            .onStart {
                _infoFilmsCloud.value = (NetworkResult.Loading())
            }
            .catch {
                _infoFilmsCloud.value = NetworkResult.Error(it.localizedMessage!!)
            }
            .onEach {
                _infoFilmsCloud.value = when (it) {
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