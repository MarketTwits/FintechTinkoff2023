package com.example.fintechtinkoff2023.domain

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.error.ErrorType
import com.example.fintechtinkoff2023.domain.mapper.ErrorTypeDomainToUiMapper
import com.example.fintechtinkoff2023.domain.mapper.FavoriteFilmsComparisonMapper
import com.example.fintechtinkoff2023.domain.mapper.FilmUiToDomainFilmMapper
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmInfo
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

interface FilmInteract {
    suspend fun fetchTopFilms(): Flow<List<FilmUi>>
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
                    cacheDataSource.getData().collect {
                        emit(favoriteFilmsComparisonMapper.compare(data.data, it))
                    }
                }
                is NetworkResult.Error -> emit(listOf(FilmUi.Failed(errorToUi.map(data.errorType))))
                is NetworkResult.Error.NotFound -> emit(listOf(FilmUi.Failed.FilmNotFound()))
                is NetworkResult.Loading -> emit(listOf(FilmUi.Progress))
            }
        }

        override suspend fun fetchSearchFilms(keywords: String) = channelFlow {
            send(listOf(FilmUi.Progress))
            when (val data = filmRepository.fetchSearchMovie(keywords)) {
                is NetworkResult.Success -> {
                    cacheDataSource.getData()
                        .collect { cachedList ->
                            val compareList =
                                favoriteFilmsComparisonMapper.compare(data.data, cachedList)
                            send(compareList)
                        }
                }
                is NetworkResult.Error -> send(listOf(FilmUi.Failed(errorToUi.map(data.errorType))))
                is NetworkResult.Error.NotFound -> send(listOf(FilmUi.Failed.FilmNotFound()))
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
                is NetworkResult.Error.NotFound -> emit(FilmInfoUi.Failed.FilmNotFound(errorToUi.map(data.errorType)))
                is NetworkResult.Loading -> emit(FilmInfoUi.Progress)
            }
        }

        override suspend fun fetchFavoriteFilms() = flow {
            filmRepository.fetchFavoriteFilms().collect{
                val data = it.map { it.map(Film.Mapper.ToFavoriteUi()) }
                emit(data)
            }
        }

        override suspend fun addOrRemoveFilm(film: FilmUi) {
            val filmBase = filmUiToDomainMapper.map(film)
            filmRepository.addFilmsToFavorite(filmBase)
        }
    }
}