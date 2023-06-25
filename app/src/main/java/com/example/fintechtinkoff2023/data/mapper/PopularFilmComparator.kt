package com.example.fintechtinkoff2023.data.mapper

import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao
import com.example.fintechtinkoff2023.domain.models.FilmBase

interface PopularFilmComparator {
    suspend fun insertPopularFilms(films : List<FilmBase>) : List<FilmCache>
    abstract class Abstract(
        private val mapper : FilmBaseToCacheMapper,
        private val dao : FilmFavoritesDao
        ) : PopularFilmComparator{
        override suspend fun insertPopularFilms(films: List<FilmBase>) : List<FilmCache> {
            val filmList =  arrayListOf<FilmCache>()
            for (film in mapper.map(films)) {
                val existingMovie = dao.getTopFilmById(film.filmId)
                if (existingMovie != null) {
                    val updatedMovie = existingMovie.copy()
                    filmList.add(updatedMovie)
                } else {
                    filmList.add(film)
                }
            }
            return filmList
        }
    }
    class Base(mapper : FilmBaseToCacheMapper,dao : FilmFavoritesDao ) : Abstract(mapper, dao)
}