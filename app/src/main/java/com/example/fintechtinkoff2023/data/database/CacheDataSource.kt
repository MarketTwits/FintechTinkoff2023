package com.example.fintechtinkoff2023.data.database
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao
import com.example.fintechtinkoff2023.data.mapper.FilmBaseToCacheMapper
import com.example.fintechtinkoff2023.data.mapper.PopularFilmComparator
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase
import kotlinx.coroutines.flow.Flow


interface CacheDataSource {
    suspend fun addPopularFilms(films : List<FilmBase>)
    suspend fun addFilm(film: FilmInfoBase)
    suspend fun removeFilm(filmId: Int)
    fun getMovieInfoById(filmId: Int) : FilmInfoCache?
    suspend fun getFavoriteFilmsInfo(): Flow<List<FilmInfoCache>>
    suspend fun getPopularFilms() : List<FilmCache>


    class Base(
        private val mapper: FilmBaseToCacheMapper,
        private val filmFavoritesDao: FilmFavoritesDao,
    ) : CacheDataSource {
        override suspend fun addPopularFilms(films: List<FilmBase>) {
           val mappedFilms = PopularFilmComparator.Base(mapper, filmFavoritesDao).insertPopularFilms(films)
            filmFavoritesDao.insertPopularFilms(mappedFilms)
        }
        override suspend fun addFilm(film: FilmInfoBase) {
            filmFavoritesDao.insertFavoriteFilmInfo(mapper.map(film))
        }

        override suspend fun removeFilm(filmId: Int) {
            filmFavoritesDao.deleteFavoriteFilmInfo(filmId)
        }

        override fun getMovieInfoById(filmId: Int): FilmInfoCache? {
            return filmFavoritesDao.getFilmInfoById(filmId.toString())
        }

        override suspend fun getFavoriteFilmsInfo(): Flow<List<FilmInfoCache>> {
            return filmFavoritesDao.getFavoritesFilmsList()
        }
        override suspend fun getPopularFilms():List<FilmCache> {
            return filmFavoritesDao.getPopularFilmsList()
        }
    }
}

