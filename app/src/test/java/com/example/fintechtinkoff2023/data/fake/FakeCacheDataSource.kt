package com.example.fintechtinkoff2023.data.fake

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCacheDataSource : CacheDataSource {
    private val filmsCache: MutableMap<Int, FilmInfoCache> = mutableMapOf()
    override suspend fun addFilm(film: FilmInfoBase) {
        val filmCache = film.map(FilmInfo.Mapper.ToCache())
        filmsCache[film.getId()] = filmCache
    }
    override suspend fun removeFilm(filmId: Int) {
        filmsCache.remove(filmId)
    }
    override fun getMovieInfoById(filmId: Int): FilmInfoCache? {
        val list = FakeDataBase.Base().fetchFake()
        val item = list.find {
            filmCache ->  filmCache.filmId == filmId
        }
       return item
    }
    override suspend fun getFavoriteFilmsInfo(): Flow<List<FilmInfoCache>> {
        return flow {
            emit(FakeDataBase.Base().fetchFake())
        }
    }
}
class FakeCacheDataSourceException : CacheDataSource{
    override suspend fun addFilm(film: FilmInfoBase) {
        throw Exception()
    }

    override suspend fun removeFilm(filmId: Int) {
        throw Exception()
    }

    override fun getMovieInfoById(filmId: Int): FilmInfoCache? {
        throw Exception()
    }

    override suspend fun getFavoriteFilmsInfo(): Flow<List<FilmInfoCache>> {
        throw Exception()
    }

}
interface FakeDataBase
{
    fun fetchFake() : List<FilmInfoCache>
    class Base() : FakeDataBase{
        override fun fetchFake(): List<FilmInfoCache> {
            val filmInfoCache1 = FilmInfoCache(
                country = listOf(Country("Country1")),
                filmId = 1, genres = listOf(Genre("Genre1")),
                name = "Фильм1", posterUrl = "https://example.com/poster1.jpg",
                description = "Film 1 description", year = "2021"
            )
            val filmInfoCache2 = FilmInfoCache(
                country = listOf(Country("Country2")),
                filmId = 2, genres = listOf(Genre("Genre2")),
                name = "Фильм2", posterUrl = "https://example.com/poster2.jpg",
                description = "Film 2 description", year = "2022"
            )
            return listOf(filmInfoCache1, filmInfoCache2)
        }
    }
}