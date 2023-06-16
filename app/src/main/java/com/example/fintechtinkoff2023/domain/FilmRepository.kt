package com.example.fintechtinkoff2023.domain

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.mapper.FilmsCacheToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FilmRepository {
    suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>>
    suspend fun fetchSearchMovie(keywords: String): NetworkResult<List<FilmBase>>
    suspend fun fetchInfoAboutFilm(filmId: Int): NetworkResult<FilmInfoBase>
    suspend fun fetchFavoriteFilms(): Flow<List<FilmBase>>
    suspend fun addOrRemove(baseFilm: FilmBase): NetworkResult<FilmBase>
    class Base(
        private val cacheDataSource: CacheDataSource,
        private val cloudDataSource: FilmsCloudDataSource,
        private val errorTypeDomainMapper: ErrorTypeDomainMapper,
        private val filmCloudMapper: FilmsCloudToDomainFilmMapper,
        private val filmCacheMapper: FilmsCacheToDomainFilmMapper,
    ) : FilmRepository {
        override suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>> {
            try {
                val data = cloudDataSource.fetchTopMovie()
                if (data.isEmpty()) {
                    return NetworkResult.NotFound()
                }
                return NetworkResult.Success(filmCloudMapper.mapFilms(data))
            } catch (e: Exception) {
                return NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }

        override suspend fun fetchSearchMovie(keywords: String): NetworkResult<List<FilmBase>> {
            try {
                val data = cloudDataSource.fetchSearchMovie(keywords)
                if (data.isEmpty()) {
                    return NetworkResult.NotFound()
                }
                return NetworkResult.Success(filmCloudMapper.mapFilms(data))
            } catch (e: Exception) {
                return NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }
        override suspend fun fetchInfoAboutFilm(filmId: Int): NetworkResult<FilmInfoBase> {
            return try {
                val cache = cacheDataSource.getMovieInfoById(filmId)
                if (cache != null) {
                    NetworkResult.Success(data = filmCacheMapper.map(cache))
                } else {
                    val data = cloudDataSource.fetchInfoAboutFilm(filmId)
                    if (data != null) {
                        NetworkResult.Success(data = filmCloudMapper.mapFilm(data))
                    } else {
                        NetworkResult.NotFound()
                    }
                }
            } catch (e: Exception) {
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

        override suspend fun addOrRemove(baseFilm: FilmBase): NetworkResult<FilmBase> {
            return try {
                val cache = cacheDataSource.getMovieInfoById(baseFilm.getId())
                if (cache != null) {
                    cacheDataSource.removeFilm(baseFilm.getId())
                } else {
                    val film = fetchInfoAboutFilm(baseFilm.getId()).data
                    cacheDataSource.addFilm(film)
                }
                NetworkResult.Success(baseFilm)
            } catch (e: Exception) {
                NetworkResult.Error(errorTypeDomainMapper.map(e))
            }
        }
    }
}