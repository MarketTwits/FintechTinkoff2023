package com.example.fintechtinkoff2023.domain

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.mapper.ErrorTypeDomainToUiMapper
import com.example.fintechtinkoff2023.domain.mapper.FavoriteFilmsComparisonMapper
import com.example.fintechtinkoff2023.domain.mapper.FilmUiToDomainFilmMapper
import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface FilmInteract {
    suspend fun fetchTopFilms(): Flow<List<FilmUi>>
    suspend fun updateTipFilms()
    suspend fun fetchSearchFilms(keywords: String): Flow<List<FilmUi>>
    suspend fun fetchInfoFilm(filmId: Int): Flow<FilmInfoUi>
    suspend fun fetchFavoriteFilms(): Flow<List<FilmUi>>
    suspend fun addOrRemoveFilm(film: FilmUi)


    class Base(
        private val cacheDataSource: CacheDataSource,
        private val favoriteFilmsComparisonMapper: FavoriteFilmsComparisonMapper,
        private val errorToUi: ErrorTypeDomainToUiMapper,
        private val filmUiToDomainMapper: FilmUiToDomainFilmMapper,
        private val filmRepository: FilmRepository,
    ) : FilmInteract {

        override suspend fun fetchTopFilms() = flow {
            emit(listOf(FilmUi.Progress))
            when (val data = filmRepository.fetchTopMovie()) {
                is NetworkResult.Success -> {
                    cacheDataSource.getFavoriteFilmsInfo().collect {
                        emit(favoriteFilmsComparisonMapper.compare(data.data, it))
                    }
                }

                is NetworkResult.Error -> emit(listOf(FilmUi.Failed(errorToUi.map(data.errorType))))
                is NetworkResult.NotFound -> emit(listOf(FilmUi.Failed.FilmNotFound()))
                is NetworkResult.Loading -> emit(listOf(FilmUi.Progress))
            }
        }

        override suspend fun fetchSearchFilms(keywords: String) = channelFlow {
            send(listOf(FilmUi.Progress))
            when (val data = filmRepository.fetchSearchMovie(keywords)) {
                is NetworkResult.Success -> {
                    cacheDataSource.getFavoriteFilmsInfo().collect { cachedList ->
                            val compareList =
                                favoriteFilmsComparisonMapper.compare(data.data, cachedList)
                            send(compareList)
                        }
                }
                is NetworkResult.Error -> send(listOf(FilmUi.Failed(errorToUi.map(data.errorType))))
                is NetworkResult.NotFound -> send(listOf(FilmUi.Failed.FilmNotFound()))
                is NetworkResult.Loading -> send(listOf(FilmUi.Progress))
            }
        }

        override suspend fun fetchInfoFilm(filmId: Int) = flow {
            emit(FilmInfoUi.Progress)
            when (val data = filmRepository.fetchInfoAboutFilm(filmId)) {
                is NetworkResult.Success -> {
                    emit(data.data.map(FilmInfo.Mapper.ToInfoUi()))
                }
                is NetworkResult.Error -> emit(FilmInfoUi.Failed(errorToUi.map(data.errorType)))
                is NetworkResult.NotFound -> emit(FilmInfoUi.NotFound(errorToUi.map(data.errorType)))
                is NetworkResult.Loading -> emit(FilmInfoUi.Progress)
            }
        }

        override suspend fun fetchFavoriteFilms() = flow {
            filmRepository.fetchFavoriteFilms().collect {
                val data = it.map { it.map(Film.Mapper.ToFavoriteUi()) }
                emit(data)
            }
        }

        override suspend fun addOrRemoveFilm(film: FilmUi) {
            filmRepository.addOrRemove(filmUiToDomainMapper.map(film))
        }

        override suspend fun updateTipFilms() {
            filmRepository.updateTopMovie()
        }
    }
}