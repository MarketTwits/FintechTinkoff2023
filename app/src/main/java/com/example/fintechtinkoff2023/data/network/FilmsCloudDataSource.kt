package com.example.fintechtinkoff2023.data.network

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilm
import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi
import com.example.fintechtinkoff2023.domain.base_source.FavoriteFilmsComparisonMapper
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmInfo
import com.example.fintechtinkoff2023.domain.model.FilmInfoBase
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception


interface FilmsCloudDataSource {
    suspend fun fetchTopMovie(): List<TopFilm>
    suspend fun fetchSearchMovie(text: String): List<SearchFilm>
    suspend fun fetchInfoAboutFilm(filmId: Int): InfoFilmCloud
    class Base(private val kinoPoiskApi: KinoPoiskApi) : FilmsCloudDataSource {
        override suspend fun fetchTopMovie() = kinoPoiskApi.getTopFilms().topFilms
        override suspend fun fetchSearchMovie(text: String) =
            kinoPoiskApi.getFilmsByKeyWords(text).searchFilms

        override suspend fun fetchInfoAboutFilm(filmId: Int): InfoFilmCloud =
            kinoPoiskApi.getInfoAboutFilmById(filmId)
    }
}

interface TestRepository {
    suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>>
    suspend fun fetchSearchMovie(keywords: String): NetworkResult<List<FilmBase>>
    suspend fun fetchInfoAboutFilm(filmId: Int): NetworkResult<FilmInfoBase>
    suspend fun fetchFavoriteFilms(): Flow<List<FilmBase>>
    suspend fun addFilmsToFavorite(baseFilm: FilmBase)

    class Base(
        private val cacheDataSource: CacheDataSource,
        private val cloudDataSource: FilmsCloudDataSource,
        private val filmMapper: FilmsCloudToDomainFilmMapper,
    ) : TestRepository {
        override suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>> {
            try {
                val data = cloudDataSource.fetchTopMovie()
                if (data.isEmpty()) {
                    return NetworkResult.Error.NotFound()
                }
                return NetworkResult.Success(filmMapper.mapFilms(data))
            } catch (e: Exception) {
                return NetworkResult.Error(e.message!!)
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
                return NetworkResult.Error(e.message!!)
            }
        }

        override suspend fun fetchInfoAboutFilm(filmId: Int): NetworkResult<FilmInfoBase> {
            return try {
                val data = cloudDataSource.fetchInfoAboutFilm(filmId)
                NetworkResult.Success(data = filmMapper.mapFilm(data))
            } catch (e: Exception) {
                NetworkResult.Error(e.message!!)
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

//        fun getInfoAboutFilm(filmId: Int) {
//            scope.launch {
//                try {
//                    val infoFilmsResponse = movieApi.getInfoAboutFilmById(filmId)
//                    infoFilmCloud.emit(NetworkResult.Success(infoFilmsResponse))
//                } catch (e: Exception) {
//                    infoFilmCloud.emit(NetworkResult.Error(e.message!!))
//                }
//            }
//        }
//
//        suspend fun getFavoriteFilms() {
//            scope.launch {
//                cacheDataSource.getData().collect {
//                    val data = it.map { filmCache ->
//                        filmCache.map(Film.Mapper.ToFavoriteUi())
//                    }
//                    favoritesFilms.emit(data)
//                }
//            }
//        }
//
//        suspend fun addFilmsToFavorite(baseFilm: FilmBase) {
//            cacheDataSource.addOrRemove(baseFilm)
//        }


interface FilmInteractor {
    suspend fun fetchTopFilms()
    suspend fun fetchSearchFilms(keywords: String)
    suspend fun fetchInfoFilm(filmId: Int)
    suspend fun fetchFavoriteFilms()
    suspend fun addOrRemoveFilm(film: FilmUi)
    val topFilms: MutableSharedFlow<List<FilmUi>>
    val searchFilms: MutableSharedFlow<List<FilmUi>>
    val infoAboutFilm: MutableSharedFlow<FilmInfoUi>
    val favoriteFilms: MutableSharedFlow<List<FilmUi>>

    class Base(
        private val cacheDataSource: CacheDataSource,
        private val mapper: FavoriteFilmsComparisonMapper,
        private val testRepository: TestRepository,
    ) : FilmInteractor {
        val scope = CoroutineScope(Dispatchers.IO)
        override suspend fun fetchTopFilms() {
            scope.launch {
                topFilms.emit(listOf(FilmUi.Progress))
                when (val data = testRepository.fetchTopMovie()) {
                    is NetworkResult.Success -> {
                        cacheDataSource.getData().collect {
                            topFilms.emit(mapper.compare(data.data))
                        }
                    }

                    is NetworkResult.Error ->
                        topFilms.emit(listOf(FilmUi.Failed(data.message)))

                    is NetworkResult.Error.NotFound ->
                        topFilms.emit(listOf(FilmUi.Failed.FilmNotFound()))

                    is NetworkResult.Loading ->
                        topFilms.emit(listOf(FilmUi.Progress))
                }
            }
        }

        override suspend fun fetchSearchFilms(keywords: String) {
            scope.launch {
                topFilms.emit(listOf(FilmUi.Progress))
                when (val data = testRepository.fetchSearchMovie(keywords)) {
                    is NetworkResult.Success -> {
                        cacheDataSource.getData().collect {
                            topFilms.emit(mapper.compare(data.data))
                        }
                    }

                    is NetworkResult.Error ->
                        topFilms.emit(listOf(FilmUi.Failed(data.message)))

                    is NetworkResult.Error.NotFound ->
                        topFilms.emit(listOf(FilmUi.Failed.FilmNotFound()))

                    is NetworkResult.Loading ->
                        topFilms.emit(listOf(FilmUi.Progress))
                }
            }
        }

        override suspend fun fetchInfoFilm(filmId: Int) {
            scope.launch {
                infoAboutFilm.emit(FilmInfoUi.Progress)
                when (val data = testRepository.fetchInfoAboutFilm(filmId)) {
                    is NetworkResult.Success -> {
                        infoAboutFilm.emit(data.data.map(FilmInfo.Mapper.ToInfoUi()))
                    }

                    is NetworkResult.Error ->
                        infoAboutFilm.emit(FilmInfoUi.Failed(data.message))

                    is NetworkResult.Error.NotFound ->
                        infoAboutFilm.emit(FilmInfoUi.Failed.FilmNotFound())

                    is NetworkResult.Loading ->
                        infoAboutFilm.emit(FilmInfoUi.Progress)
                }
            }
        }

        override suspend fun fetchFavoriteFilms() {
            scope.launch {
                testRepository.fetchFavoriteFilms().collect {
                    val data = it.map {
                        it.map(Film.Mapper.ToFavoriteUi())
                    }
                    favoriteFilms.emit(data)
                }
            }
        }

        override suspend fun addOrRemoveFilm(film: FilmUi) {
            //todo add FilmUi to DomainMapper
            scope.launch {
                val filmBase = FilmBase(
                    film.filmId,
                    film.nameRu,
                    film.posterUrl,
                    film.year
                )
                testRepository.addFilmsToFavorite(filmBase)
            }
        }

        override val searchFilms: MutableSharedFlow<List<FilmUi>> = MutableSharedFlow()
        override val infoAboutFilm: MutableSharedFlow<FilmInfoUi> = MutableSharedFlow()
        override val favoriteFilms: MutableSharedFlow<List<FilmUi>> = MutableSharedFlow()
        override val topFilms: MutableSharedFlow<List<FilmUi>> = MutableSharedFlow()
    }
}