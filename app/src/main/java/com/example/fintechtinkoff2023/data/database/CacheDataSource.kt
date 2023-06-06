package com.example.fintechtinkoff2023.data.database

import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao
import com.example.fintechtinkoff2023.data.mapper.FilmBaseToCacheMapper
import com.example.fintechtinkoff2023.domain.model.FilmBase
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {
    suspend fun addOrRemove(film: FilmBase)
    fun getMovieById(filmId: Int): FilmCache?
    suspend fun getData(): Flow<List<FilmCache>>
    suspend fun getDataLiveData(): Flow<List<FilmCache>>

    class Base(
        private val mapper: FilmBaseToCacheMapper,
        private val filmFavoritesDao: FilmFavoritesDao,
    ) : CacheDataSource {

        override suspend fun addOrRemove(film: FilmBase) {
            val filmCached = mapper.map(film)
            val existingMovie = filmFavoritesDao.getFilmById(filmCached.filmId.toString())
            if (existingMovie == null) {
                filmFavoritesDao.insertFavoritesFilm(filmCached)
            } else {
                filmFavoritesDao.deleteFavoriteFilm(filmCached.filmId)
            }
        }

        override fun getMovieById(filmId: Int): FilmCache? {
            return filmFavoritesDao.getFilmById(filmId.toString())
        }

        override suspend fun getData(): Flow<List<FilmCache>> {
            return filmFavoritesDao.getFavoritesFilmsList()
        }

        override suspend fun getDataLiveData(): Flow<List<FilmCache>> {
            return filmFavoritesDao.getFavoritesFilmsList()
        }
    }
}

