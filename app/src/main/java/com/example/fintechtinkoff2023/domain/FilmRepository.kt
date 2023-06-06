package com.example.fintechtinkoff2023.domain

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.fintechtinkoff2023.core.wrappers.Logger
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmInfoBase
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

interface FilmRepository {
    suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>>
    suspend fun fetchSearchMovie(keywords: String): NetworkResult<List<FilmBase>>
    suspend fun fetchInfoAboutFilm(filmId: Int): NetworkResult<FilmInfoBase>
    suspend fun fetchFavoriteFilms(): Flow<List<FilmBase>>
    suspend fun addFilmsToFavorite(baseFilm: FilmBase)
    suspend fun fetchSearchMovieFlow(keywords: String): Flow<NetworkResult<List<FilmBase>>>
    class Base(
        private val cacheDataSource: CacheDataSource,
        private val cloudDataSource: FilmsCloudDataSource,
        private val errorTypeDomainMapper: ErrorTypeDomainMapper,
        private val filmMapper: FilmsCloudToDomainFilmMapper,
    ) : FilmRepository {
        override suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>> {
            try {
                val data = cloudDataSource.fetchTopMovie()
                if (data.isEmpty()) {
                    return NetworkResult.Error.NotFound()
                }
                return NetworkResult.Success(filmMapper.mapFilms(data))
            } catch (e: Exception) {
                return NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }

        override suspend fun fetchSearchMovie(keywords: String): NetworkResult<List<FilmBase>> {
            try {
                val data = cloudDataSource.fetchSearchMovie(keywords)
                if (data.isEmpty()) {
                    return NetworkResult.Error.NotFound()
                }
                return NetworkResult.Success(filmMapper.mapFilms(data))
            } catch (e: Exception) {
                return NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }
        override suspend fun fetchSearchMovieFlow(keywords: String): Flow<NetworkResult<List<FilmBase>>> = flow {
            try {
                val data = cloudDataSource.fetchSearchMovie(keywords)
                if (data.isEmpty()) {
                    emit(NetworkResult.Error.NotFound())
                } else {
                    emit(NetworkResult.Success(filmMapper.mapFilms(data)))
                }
            } catch (e: Exception) {
                emit(NetworkResult.Error(errorTypeDomainMapper.map(e)))
            }
        }

        override suspend fun fetchInfoAboutFilm(filmId: Int): NetworkResult<FilmInfoBase> {
            return try {
                val data = cloudDataSource.fetchInfoAboutFilm(filmId)
                NetworkResult.Success(data = filmMapper.mapFilm(data))
            } catch (e: Exception) {
                Logger.Base().logError(message = e.message.toString())
                NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }

        override suspend fun fetchFavoriteFilms(): Flow<List<FilmBase>> {
            return cacheDataSource.getData().map {
                it.map {
                    it.map(Film.Mapper.ToDomain())
                }
            }

        }
        override suspend fun addFilmsToFavorite(baseFilm: FilmBase) {
            cacheDataSource.addOrRemove(baseFilm)
        }
    }
}