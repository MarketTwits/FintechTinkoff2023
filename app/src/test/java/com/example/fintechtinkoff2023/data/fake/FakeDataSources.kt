package com.example.fintechtinkoff2023.data.fake

import com.example.fintechtinkoff2023.data.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.data.network.models.item_film.FilmInfoCloud
import com.example.fintechtinkoff2023.data.network.models.page_film.TopFilm
import com.example.fintechtinkoff2023.data.network.models.search_films.SearchFilm
import com.example.fintechtinkoff2023.domain.model.FilmInfoBase
import kotlinx.coroutines.flow.Flow

class FakeCloudDataSource : FilmsCloudDataSource {
    override suspend fun fetchTopMovie(): List<TopFilm> {
        val topFilm1 = TopFilm(
            countries = listOf(Country("Country1")),
            filmId = 1, filmLength = "120 minutes", genres = listOf(Genre("Genre1")),
            nameEn = "Film1", nameRu = "Фильм1", posterUrl = "https://example.com/poster1.jpg",
            posterUrlPreview = "https://example.com/poster1_preview.jpg", rating = "8.5", ratingChange = 0,
            ratingVoteCount = 100, year = "2021"
        )
        val topFilm2 = TopFilm(
            countries = listOf(Country("Country2")), filmId = 2,
            filmLength = "90 minutes", genres = listOf(Genre("Genre2")), nameEn = "Film2",
            nameRu = "Фильм2", posterUrl = "https://example.com/poster2.jpg",
            posterUrlPreview = "https://example.com/poster2_preview.jpg", rating = "7.8", ratingChange = 0,
            ratingVoteCount = 80, year = "2022"
        )
        return listOf(topFilm1, topFilm2)
    }

    override suspend fun fetchSearchMovie(text: String): List<SearchFilm> {
        val fakeSearchFilm1 = SearchFilm(
            listOf(
                Country("Country1")
            ), "Film 1 description", 1,
            "120 minutes", listOf(Genre("Genre1")), "Film1", "Фильм1",
            "https://example.com/poster1.jpg", "https://example.com/poster1_preview.jpg",
            "8.5", 100, "Type1", "2021"
        )
        val fakeSearchFilm2 = SearchFilm(
            listOf(Country("Country2")), "Film 2 description", 2, "90 minutes",
            listOf(Genre("Genre2")), "Film2", "Фильм2", "https://example.com/poster2.jpg",
            "https://example.com/poster2_preview.jpg", "7.8", 80,
            "Type2", "2022"
        )
        return listOf(fakeSearchFilm1, fakeSearchFilm2)
    }

    override suspend fun fetchInfoAboutFilm(filmId: Int): FilmInfoCloud {
        return FilmInfoCloud(
            countries = listOf(Country("Country1")),
            kinopoiskId = 1, genres = listOf(Genre("Genre1")),
            name = "Фильм1", posterUrl = "https://example.com/poster1.jpg",
            description = "Film 1 description", year = "1"
        )
    }
}

class FakeCloudDataSourceThrowException : FilmsCloudDataSource {
    override suspend fun fetchTopMovie(): List<TopFilm> = throw Exception()
    override suspend fun fetchSearchMovie(text: String): List<SearchFilm> = throw Exception()
    override suspend fun fetchInfoAboutFilm(filmId: Int): FilmInfoCloud = throw Exception()
}

class FakeCloudDataSourceWithEmptyList : FilmsCloudDataSource {
    override suspend fun fetchTopMovie(): List<TopFilm> = emptyList()
    override suspend fun fetchSearchMovie(text: String): List<SearchFilm> = emptyList()
    override suspend fun fetchInfoAboutFilm(filmId: Int): FilmInfoCloud? = null
}

class FakeCacheDataSource : CacheDataSource {
    override suspend fun addFilm(film: FilmInfoBase) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFilm(filmId: Int) {
        TODO("Not yet implemented")
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