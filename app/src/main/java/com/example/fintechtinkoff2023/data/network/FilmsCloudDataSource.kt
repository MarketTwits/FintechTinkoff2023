package com.example.fintechtinkoff2023.data.network

import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import java.lang.Exception

interface FilmsCloudDataSource {
    suspend fun fetchTopMovie(): List<TopFilm>
    class Base(private val kinoPoiskApi: KinoPoiskApi) : FilmsCloudDataSource {
        override suspend fun fetchTopMovie(): List<TopFilm> {
            return kinoPoiskApi.getTopFilms().topFilms
        }
    }
}

interface TestRepository {
    suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>>
    class Base(
        private val filmsCloudDataSource: FilmsCloudDataSource,
        private val filmMapper: FilmsCloudToDomainFilmMapper
    ) : TestRepository {
        override suspend fun fetchTopMovie(): NetworkResult<List<FilmBase>> {
            return try {
                val data = filmsCloudDataSource.fetchTopMovie()
                if (data.isEmpty()) {
                    NetworkResult.Error.NotFound<FilmBase>("Is empty")
                }
                NetworkResult.Success(filmMapper.map(data))
            } catch (e: Exception) {
                NetworkResult.Error(e.message)
            }
        }
    }
}

interface FilmDomainToUiMapper {
    suspend fun map(films: NetworkResult<List<FilmBase>>): List<FilmUi>
    class Base(
        private val testRepository: TestRepository
    ) : FilmDomainToUiMapper {
        override suspend fun map(films: NetworkResult<List<FilmBase>>): List<FilmUi> {
            return when (val data = testRepository.fetchTopMovie()) {
                is NetworkResult.Success -> data.data?.map {
                    it.map(Film.Mapper.ToBaseUi())
                }!!
                is NetworkResult.Error -> data.message?.map {
                    FilmUi.Failed(data.message)
                }!!
                is NetworkResult.Error.NotFound -> listOf(FilmUi.Failed.FilmNotFound())
                is NetworkResult.Loading -> listOf(FilmUi.Progress)
            }
        }
    }
}