package com.example.fintechtinkoff2023.domain.base_source

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilm
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.flow.first

interface ItemsTopComparison {
    suspend fun compare(movies: List<TopFilm>): ArrayList<FilmUi>
    class TopFilmCompare(private val cacheDataSource: CacheDataSource) : ItemsTopComparison {
        override suspend fun compare(movies: List<TopFilm>): ArrayList<FilmUi> {
            val items = arrayListOf<FilmUi>()
            val favoriteList = cacheDataSource.getData().first()
            movies.map { film ->
                val isFavorite = favoriteList.any { it.filmId == film.filmId }
                if (isFavorite) {
                    val favorite = FilmUi.Favorite(
                        film.filmId,
                        film.nameRu ?: film.nameEn,
                        film.posterUrl,
                        film.year
                    )
                    items.add(favorite)
                } else {
                    val base = FilmUi.Base(
                        film.filmId,
                        film.nameRu ?: film.nameEn,
                        film.posterUrl,
                        film.year
                    )
                    items.add(base)
                }
            }
            return items
        }
    }
}

interface ItemsSearchComparison {
    suspend fun compare(movies: List<SearchFilm>): ArrayList<FilmUi>
    class SearchFilmCompare(private val cacheDataSource: CacheDataSource) : ItemsSearchComparison {
        override suspend fun compare(movies: List<SearchFilm>): ArrayList<FilmUi> {
            val items = arrayListOf<FilmUi>()
            val favoriteList = cacheDataSource.getData().first()
            movies.map { film ->
                val isFavorite = favoriteList.any { it.filmId == film.filmId }
                if (isFavorite) {
                    val favorite = FilmUi.Favorite(
                        film.filmId,
                        film.nameRu ?: film.nameEn,
                        film.posterUrl,
                        film.year
                    )
                    items.add(favorite)
                } else {
                    val base = FilmUi.Base(
                        film.filmId,
                        film.nameRu ?: film.nameEn,
                        film.posterUrl,
                        film.year
                    )
                    items.add(base)
                }
            }
            return items
        }
    }
}

interface FavoriteFilmsComparisonMapper {
    suspend fun compare(movies: List<FilmBase>): List<FilmUi>
    class Base(private val cacheDataSource: CacheDataSource) : FavoriteFilmsComparisonMapper {
        override suspend fun compare(movies: List<FilmBase>): List<FilmUi> {
            val items = arrayListOf<FilmUi>()
            val favoriteList = cacheDataSource.getData().first()
            movies.map { film ->
                val isFavorite = favoriteList.any { it.filmId == film.getId() }
                if (isFavorite) {
                    val favorites = film.map(Film.Mapper.ToFavoriteUi())
                    items.add(favorites)
                } else {
                    val base = film.map(Film.Mapper.ToBaseUi())
                    items.add(base)
                }
            }
            return items
        }
    }
}