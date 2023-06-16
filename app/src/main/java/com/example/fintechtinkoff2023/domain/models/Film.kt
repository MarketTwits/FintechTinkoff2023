package com.example.fintechtinkoff2023.domain.models
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.data.network.models.item_film.FilmInfoCloud
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.models.FilmUi


interface Film {
    suspend fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        suspend fun map(
            filmId: Int,
            nameRu: String,
            posterUrl: String,
            year: String = "",
        ): T

        class ToDomain : Mapper<FilmBase> {
            override suspend fun map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ) = FilmBase(filmId, nameRu, posterUrl, year)
        }

        class ToFavoriteUi : Mapper<FilmUi> {
            override suspend fun map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ): FilmUi = FilmUi.Favorite(filmId, nameRu, posterUrl, year)
        }

        class ToBaseUi : Mapper<FilmUi> {
            override suspend fun map(
                filmId: Int,
                nameRu: String,
                posterUrl: String,
                year: String,
            ): FilmUi = FilmUi.Base(filmId, nameRu, posterUrl, year)
        }
    }
}

interface FilmInfo {
    suspend fun <T> map(mapper: Mapper<T>): T
    interface Mapper<T> {
        suspend fun map(
            filmId: Int,
            name: String,
            posterUrl: String,
            description: String,
            year: String,
            country: List<Country> = emptyList(),
            genres: List<Genre> = emptyList(),
        ): T
        class ToCloud : Mapper<FilmInfoCloud> {
            override suspend fun map(
                filmId: Int,
                name: String,
                posterUrl: String,
                description: String,
                year: String,
                country: List<Country>,
                genres: List<Genre>,
            ): FilmInfoCloud {
                return FilmInfoCloud(filmId, name, posterUrl, description, year,genres, country)
            }
        }
        class ToInfoBase : Mapper<FilmInfoBase> {
            override suspend fun map(
                filmId: Int,
                name: String,
                posterUrl: String,
                description: String,
                year: String,
                country: List<Country>,
                genres: List<Genre>,
            ): FilmInfoBase {
                return FilmInfoBase(filmId, name, posterUrl, description, year,country, genres)
            }
        }

        class ToInfoUi : Mapper<FilmInfoUi> {
            override suspend fun map(
                filmId: Int,
                name: String,
                posterUrl: String,
                description: String,
                year: String,
                country: List<Country>,
                genres: List<Genre>,
            ): FilmInfoUi {
                return FilmInfoUi.Base(filmId, name, posterUrl, description, country, genres)
            }
        }

        class ToCache : Mapper<FilmCache> {
            override suspend fun map(
                filmId: Int,
                name: String,
                posterUrl: String,
                description: String,
                year: String,
                country: List<Country>,
                genres: List<Genre>,
            ): FilmCache {
                return FilmCache(
                    filmId = filmId,
                    name = name,
                    posterUrl = posterUrl,
                    description = description,
                    country = country,
                    year = year,
                    genres = genres
                )
            }
        }
    }
}

data class FilmInfoBase(
    private val filmId: Int,
    private val name: String,
    private val posterUrl: String,
    private val description: String,
    private val year: String,
    private val country: List<Country> = emptyList(),
    private val genres: List<Genre> = emptyList(),
) : FilmInfo {
    override suspend fun <T> map(mapper: FilmInfo.Mapper<T>): T {
        return mapper.map(filmId, name, posterUrl, description, year,country, genres)
    }
    fun getId() : Int = filmId
}

data class FilmBase(
    private val filmId: Int,
    private val name: String,
    private val posterUrl: String,
    private val year: String,
) : Film {
    override suspend fun <T> map(mapper: Film.Mapper<T>): T =
        mapper.map(filmId, name, posterUrl, year)

     fun getId(): Int = filmId
}
