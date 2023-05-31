package com.example.fintechtinkoff2023.domain

import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.flow.first


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