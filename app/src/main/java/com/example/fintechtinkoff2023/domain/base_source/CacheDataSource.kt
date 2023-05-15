package com.example.fintechtinkoff2023.domain.base_source

import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {
    suspend fun addOrRemove(film: FilmBase)
    fun getMovieById(filmId: Int): FilmCache?
    suspend fun getData(): Flow<List<FilmCache>>
    //suspend fun getData() : List<FilmBase>

    class Base(
        private val filmFavoritesDao: FilmFavoritesDao,
    ) : CacheDataSource {


        override suspend fun addOrRemove(film: FilmBase) {
            val filmCached = film.map(Film.Mapper.ToCache())
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

//        override suspend fun getData(): List<FilmBase> {
//            val data = filmFavoritesDao.getFavoritesFilmsList()
//            return if (data.isEmpty()) {
//                    emptyList()
//            } else {
//                val favoriteBaseFilms = arrayListOf<FilmBase>()
//                data.forEach {
//                    favoriteBaseFilms.add(it.map(Film.Mapper.ToDomain()))
//                }
//                favoriteBaseFilms
//            }
//        }
        override suspend fun getData(): Flow<List<FilmCache>> {

            val data = filmFavoritesDao.getFavoritesFilmsList()
//                if (checkNotNull(data.value).isEmpty()) {
//                    filmsFlow.value = emptyList()
//                } else {
//                    val favoriteBaseFilms = arrayListOf<FilmBase>()
//                    checkNotNull(data.value) .forEach {
//                        favoriteBaseFilms.add(it.map(Film.Mapper.ToDomain()))
//                    }
//                    filmsFlow.value = favoriteBaseFilms
//                }
            return data
        }
    }
}

