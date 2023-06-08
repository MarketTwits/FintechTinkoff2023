package com.example.fintechtinkoff2023.data.database
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao
import com.example.fintechtinkoff2023.data.mapper.FilmBaseToCacheMapper
import com.example.fintechtinkoff2023.domain.model.FilmInfoBase
import kotlinx.coroutines.flow.Flow


interface CacheDataSource {
    suspend fun addFilm(film: FilmInfoBase)
    suspend fun removeFilm(filmId: Int)
    fun getMovieInfoById(filmId: Int) : FilmCache?
    suspend fun getData(): Flow<List<FilmCache>>
    suspend fun getDataLiveData(): Flow<List<FilmCache>>

    class Base(
        private val mapper: FilmBaseToCacheMapper,
        private val filmFavoritesDao: FilmFavoritesDao,
    ) : CacheDataSource {
        override suspend fun addFilm(film: FilmInfoBase) {
            filmFavoritesDao.insertFavoriteFilmInfo(mapper.map(film))
        }

        override suspend fun removeFilm(filmId: Int) {
            filmFavoritesDao.deleteFavoriteFilmInfo(filmId)
        }

        override fun getMovieInfoById(filmId: Int): FilmCache? {
            return filmFavoritesDao.getFilmInfoById(filmId.toString())
        }

        override suspend fun getData(): Flow<List<FilmCache>> {
            return filmFavoritesDao.getFavoritesFilmsList()

        }

        override suspend fun getDataLiveData(): Flow<List<FilmCache>> {
            return filmFavoritesDao.getFavoritesFilmsList()
        }
    }
}

