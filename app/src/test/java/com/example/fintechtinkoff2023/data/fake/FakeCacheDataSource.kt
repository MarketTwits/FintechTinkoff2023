package com.example.fintechtinkoff2023.data.fake

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.model.FilmInfoBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCacheDataSource : CacheDataSource {
    override suspend fun addFilm(film: FilmInfoBase) {
        throw Exception()
    }

    override suspend fun removeFilm(filmId: Int) {

    }

    override fun getMovieInfoById(filmId: Int): FilmCache? {
        val filmCache1 = FilmCache(
            country = listOf(Country("Country1")),
            filmId = 1, genres = listOf(Genre("Genre1")),
            name = "Фильм1", posterUrl = "https://example.com/poster1.jpg",
            description = "Film 1 description", year = "2021"
        )
        val filmCache2 = FilmCache(
            country = listOf(Country("Country2")),
            filmId = 2, genres = listOf(Genre("Genre2")),
            name = "Фильм1", posterUrl = "https://example.com/poster2.jpg",
            description = "Film 2 description", year = "2022"
        )
        val list = listOf(filmCache1, filmCache2)
        val item = list.find {
            filmCache ->  filmCache.filmId == filmId
        }
       return item
    }

    override suspend fun getData(): Flow<List<FilmCache>> {
        return flow {
            val filmCache1 = FilmCache(
                country = listOf(Country("Country1")),
                filmId = 1, genres = listOf(Genre("Genre1")),
                name = "Фильм1", posterUrl = "https://example.com/poster1.jpg",
                description = "Film 1 description", year = "2021"
            )
            val filmCache2 = FilmCache(
                country = listOf(Country("Country2")),
                filmId = 2, genres = listOf(Genre("Genre2")),
                name = "Фильм2", posterUrl = "https://example.com/poster2.jpg",
                description = "Film 2 description", year = "2022"
            )
            val list = listOf(filmCache1, filmCache2)
            emit(list)
        }
    }
    override suspend fun getDataLiveData(): Flow<List<FilmCache>> {
        TODO("Not yet implemented")
    }
}
class FakeCacheDataSourceException : CacheDataSource{
    override suspend fun addFilm(film: FilmInfoBase) {
        throw Exception()
    }

    override suspend fun removeFilm(filmId: Int) {
        throw Exception()
    }

    override fun getMovieInfoById(filmId: Int): FilmCache? {
      return null
    }

    override suspend fun getData(): Flow<List<FilmCache>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataLiveData(): Flow<List<FilmCache>> {
        TODO("Not yet implemented")
    }
}