package com.example.fintechtinkoff2023.domain

import android.util.Log
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilm
import com.example.fintechtinkoff2023.data.network.retrofit.RetrofitInstance
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilmPage
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilmsPage


import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FilmsRepositoryImpl {

    private val movieApi = RetrofitInstance.kinoPoiskApiInstance

    val topFilms = MutableSharedFlow<NetworkResult<TopFilmPage>>()

    val searchFilms = MutableSharedFlow<NetworkResult<SearchFilmsPage>>()

    val infoFilm = MutableSharedFlow<NetworkResult<InfoFilm>>()

    private val excetionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainViewModel", "Exception caught: $throwable")
    }
    private val scope = CoroutineScope(Dispatchers.IO + excetionHandler)

    fun getTopMovie() {
        scope.launch {
            try {
                val pageFilms = movieApi.getTopFilms()
                topFilms.emit(NetworkResult.Success(pageFilms))
            } catch (e: Exception) {
                topFilms.emit(NetworkResult.Error(e.message))
            }
        }
    }

    fun getSearchMovie(keywords: String) {
        scope.launch {
            try {
                val pageFilms = movieApi.getFilmsByKeyWords(keywords)
                if (pageFilms.searchFilms.isEmpty()) {
                    searchFilms.emit(NetworkResult.Error("Not found"))
                } else {
                    searchFilms.emit(NetworkResult.Success(pageFilms))
                }
            } catch (e: Exception) {
                searchFilms.emit(NetworkResult.Error(e.message))
            }
        }
    }

    fun getInfoAboutFilm(filmId: Int) {
        scope.launch {
            try {
                val infoFilmsResponse = movieApi.getInfoAboutFilmById(filmId)
                infoFilm.emit(NetworkResult.Success(infoFilmsResponse))
            } catch (e: Exception) {
                infoFilm.emit(NetworkResult.Error(e.message))
            }
        }
    }
}

