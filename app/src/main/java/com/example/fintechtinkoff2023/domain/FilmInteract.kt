package com.example.fintechtinkoff2023.domain

import android.util.Log
import com.example.fintechtinkoff2023.core.wrappers.Logger
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.mapper.ErrorTypeDomainToUiMapper
import com.example.fintechtinkoff2023.domain.mapper.FilmUiToDomainFilmMapper
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmInfo
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface FilmInteract {
    suspend fun fetchTopFilms()
    suspend fun fetchSearchFilms(keywords: String)
    suspend fun fetchInfoFilm(filmId: Int)
    suspend fun fetchFavoriteFilms()
    suspend fun addOrRemoveFilm(film: FilmUi)
    val topFilms: MutableSharedFlow<List<FilmUi>>
    val searchFilms: MutableSharedFlow<List<FilmUi>>
    val infoAboutFilm: MutableSharedFlow<FilmInfoUi>
    val favoriteFilms: MutableSharedFlow<List<FilmUi>>

    class Base(
        private val cacheDataSource: CacheDataSource,
        private val favoriteFilmsComparisonMapper: FavoriteFilmsComparisonMapper,
        private val errorToUi: ErrorTypeDomainToUiMapper,
        private val filmUiToDomainMapper : FilmUiToDomainFilmMapper,
        private val filmRepository: FilmRepository,
    ) : FilmInteract {
        val scope = CoroutineScope(Dispatchers.IO)
        override suspend fun fetchTopFilms() {
            scope.launch {
                topFilms.emit(listOf(FilmUi.Progress))
                when (val data = filmRepository.fetchTopMovie()) {
                    is NetworkResult.Success -> {
                        cacheDataSource.getData().collect {
                            topFilms.emit(favoriteFilmsComparisonMapper.compare(data.data, it))
                        }
                    }
                    is NetworkResult.Error -> topFilms.emit(listOf(FilmUi.Failed(errorToUi.map(data.errorType))))
                    is NetworkResult.Error.NotFound -> topFilms.emit(listOf(FilmUi.Failed.FilmNotFound()))
                    is NetworkResult.Loading -> topFilms.emit(listOf(FilmUi.Progress))
                }
            }
        }

        override suspend fun fetchSearchFilms(keywords: String) {
            scope.launch {
                topFilms.emit(listOf(FilmUi.Progress))
                when (val data = filmRepository.fetchSearchMovie(keywords)) {
                    is NetworkResult.Success -> {
                        cacheDataSource.getData().collect {
                            topFilms.emit(favoriteFilmsComparisonMapper.compare(data.data, it))
                        }
                    }
                    is NetworkResult.Error -> topFilms.emit(listOf(FilmUi.Failed(errorToUi.map(data.errorType))))
                    is NetworkResult.Error.NotFound -> topFilms.emit(listOf(FilmUi.Failed.FilmNotFound()))
                    is NetworkResult.Loading -> topFilms.emit(listOf(FilmUi.Progress))
                }
            }
        }

        override suspend fun fetchInfoFilm(filmId: Int) {
            scope.launch {
                infoAboutFilm.emit(FilmInfoUi.Progress)
                when (val data = filmRepository.fetchInfoAboutFilm(filmId)) {
                    is NetworkResult.Success -> {
                        infoAboutFilm.emit(data.data.map(FilmInfo.Mapper.ToInfoUi()))
                    }
                    is NetworkResult.Error -> infoAboutFilm.emit(FilmInfoUi.Failed(errorToUi.map(data.errorType)))
                    is NetworkResult.Error.NotFound -> infoAboutFilm.emit(FilmInfoUi.Failed.FilmNotFound())
                    is NetworkResult.Loading -> infoAboutFilm.emit(FilmInfoUi.Progress)
                }
            }
        }

        override suspend fun fetchFavoriteFilms() {
            scope.launch {
                filmRepository.fetchFavoriteFilms().collect {
                    val data = it.map {
                        it.map(Film.Mapper.ToFavoriteUi())
                    }
                    favoriteFilms.emit(data)
                }
            }
        }

        override suspend fun addOrRemoveFilm(film: FilmUi) {
            scope.launch {
                val filmBase = filmUiToDomainMapper.map(film)
                filmRepository.addFilmsToFavorite(filmBase)
            }
        }

        override val searchFilms: MutableSharedFlow<List<FilmUi>> = MutableSharedFlow()
        override val infoAboutFilm: MutableSharedFlow<FilmInfoUi> = MutableSharedFlow()
        override val favoriteFilms: MutableSharedFlow<List<FilmUi>> = MutableSharedFlow()
        override val topFilms: MutableSharedFlow<List<FilmUi>> = MutableSharedFlow()
    }
}