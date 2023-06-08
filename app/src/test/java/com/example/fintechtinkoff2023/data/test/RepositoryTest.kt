package com.example.fintechtinkoff2023.data.test

import com.example.fintechtinkoff2023.data.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.fake.FakeCacheDataSource
import com.example.fintechtinkoff2023.data.fake.FakeCloudDataSource
import com.example.fintechtinkoff2023.data.fake.FakeCloudDataSourceThrowException
import com.example.fintechtinkoff2023.data.fake.FakeCloudDataSourceWithEmptyList
import com.example.fintechtinkoff2023.data.fake.expected.RepositoryTestExpectedData
import com.example.fintechtinkoff2023.data.mapper.FilmsCacheToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.domain.error.ErrorType
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test;

class RepositoryTest {
    private val cacheDataSource: CacheDataSource = FakeCacheDataSource()
    private val cloudDataSource: FilmsCloudDataSource = FakeCloudDataSource()
    private val errorTypeDomainMapper: ErrorTypeDomainMapper = ErrorTypeDomainMapper.Base()
    private val filmCloudMapper: FilmsCloudToDomainFilmMapper = FilmsCloudToDomainFilmMapper.Base()
    private val filmCacheMapper: FilmsCacheToDomainFilmMapper = FilmsCacheToDomainFilmMapper.Base()
    private val expectedDataRepository = RepositoryTestExpectedData.Base()

    //Top Films
    @Test
    fun fetch_top_movie_successful() = runBlocking {
        val repository = FilmRepository.Base(
            cacheDataSource,
            cloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchTopMovie()
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val expectedData = expectedDataRepository.fetchFilmsSuccessData()
        assertEquals(expectedData, successResult.data)
    }

    @Test
    fun test_fetch_top_movie_error() = runBlocking {
        val fakeCloudDataSource = FakeCloudDataSourceThrowException()
        val repository = FilmRepository.Base(
            cacheDataSource,
            fakeCloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchTopMovie()
        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals(ErrorType.GENERIC_ERROR, errorResult.errorMessage)
    }

    @Test
    fun test_fetch_top_movie_not_found() = runBlocking {
        val fakeCloudDataSource = FakeCloudDataSourceWithEmptyList()
        val repository = FilmRepository.Base(
            cacheDataSource,
            fakeCloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchTopMovie()
        assertTrue(result is NetworkResult.Error.NotFound)
        val notFoundResult = result as NetworkResult.Error.NotFound
        assertEquals(ErrorType.NOT_FOUND, notFoundResult.errorType)
    }

    //Search Films
    @Test
    fun test_fetch_search_movie_success() = runBlocking {
        val repository = FilmRepository.Base(
            cacheDataSource,
            cloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchSearchMovie("")
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val expectedData = expectedDataRepository.fetchFilmsSuccessData()
        assertEquals(expectedData, successResult.data)
    }

    @Test
    fun test_fetch_search_movie_error() = runBlocking {
        val fakeCloudDataSource = FakeCloudDataSourceThrowException()
        val repository = FilmRepository.Base(
            cacheDataSource,
            fakeCloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchSearchMovie("")
        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals(ErrorType.GENERIC_ERROR, errorResult.errorMessage)
    }

    @Test
    fun test_fetch_search_movie_not_found() = runBlocking {
        val fakeCloudDataSource = FakeCloudDataSourceWithEmptyList()
        val repository = FilmRepository.Base(
            cacheDataSource,
            fakeCloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchTopMovie()
        assertTrue(result is NetworkResult.Error.NotFound)
        val notFoundResult = result as NetworkResult.Error.NotFound
        assertEquals(ErrorType.NOT_FOUND, notFoundResult.errorType)
    }

    //Info about films
    @Test
    fun test_fetch_info_movie_success_without_cache() = runBlocking {
        val repository = FilmRepository.Base(
            cacheDataSource,
            cloudDataSource,
            errorTypeDomainMapper,
            filmCloudMapper,
            filmCacheMapper
        )
        val result = repository.fetchInfoAboutFilm(1)
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val expectedData =
            NetworkResult.Success(expectedDataRepository.fetchInfoAboutFilmSuccessData())
        val actual = NetworkResult.Success(successResult.data)
        assertEquals(expectedData, actual)
    }

}
