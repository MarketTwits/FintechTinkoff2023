package com.example.fintechtinkoff2023.data.fake

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

interface FakeCacheDataSource : CacheDataSource {
    override suspend fun addPopularFilms(films: List<FilmBase>) = Unit
    override suspend fun addFilm(film: FilmInfoBase) = Unit
    override suspend fun removeFilm(filmId: Int) = Unit

    class WithData() : FakeCacheDataSource{
        private val filmsCache: MutableMap<Int, FilmInfoCache> = mutableMapOf()
        override suspend fun addPopularFilms(films: List<FilmBase>) {
            val filmCache = films.map{it.map(Film.Mapper.ToCache())}
        }
        override suspend fun addFilm(film: FilmInfoBase) {
            val filmCache = film.map(FilmInfo.Mapper.ToCache())
            filmsCache[film.getId()] = filmCache
        }
        override suspend fun removeFilm(filmId: Int) {
            filmsCache.remove(filmId)
        }
        override fun getMovieInfoById(filmId: Int): FilmInfoCache? {
            val list = FakeCacheDataSourceData.CacheDataSource().fetchFakeInfoFilms()
            val item = list.find {
                    filmCache ->  filmCache.filmId == filmId
            }
            return item
        }
        override suspend fun getFavoriteFilmsInfo(): Flow<List<FilmInfoCache>> {
            return flow {
                emit(FakeCacheDataSourceData.CacheDataSource().fetchFakeInfoFilms())
            }
        }

        override suspend fun getPopularFilms(): List<FilmCache> {
            return FakeCacheDataSourceData.CacheDataSource().fetchFakeTopFilms()
        }
    }
    class WithException : CacheDataSource{
        override suspend fun addPopularFilms(films: List<FilmBase>) {
            throw Exception()
        }

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

        override suspend fun getPopularFilms(): List<FilmCache> {
            throw Exception()
        }
    }
    class WithEmptyData : FakeCacheDataSource {
        override fun getMovieInfoById(filmId: Int): FilmInfoCache? = null
        override suspend fun getFavoriteFilmsInfo(): Flow<List<FilmInfoCache>>  = flowOf(listOf())
        override suspend fun getPopularFilms(): List<FilmCache> = emptyList()
    }

}

interface FakeCacheDataSourceData
{
    fun fetchFakeInfoFilms() : List<FilmInfoCache>
    fun fetchFakeTopFilms() : List<FilmCache>
    class CacheDataSource() : FakeCacheDataSourceData{
        override fun fetchFakeInfoFilms(): List<FilmInfoCache> {
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

        override fun fetchFakeTopFilms(): List<FilmCache> {
            val filmBase1 = FilmCache(
                filmId = 1,
                nameRu = "Фильм1",
                posterUrl = "https://example.com/poster1_preview.jpg",
                year = "2021"
            )
            val filmBase2 = FilmCache(
                filmId = 2,
                nameRu = "Фильм2",
                posterUrl = "https://example.com/poster2_preview.jpg",
                year = "2022"
            )
            return listOf(filmBase1, filmBase2)
        }
    }
}