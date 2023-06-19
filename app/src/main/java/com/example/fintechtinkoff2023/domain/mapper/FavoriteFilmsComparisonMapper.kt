package com.example.fintechtinkoff2023.domain.mapper
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.presentation.models.FilmUi


interface FavoriteFilmsComparisonMapper {
    suspend fun compare(moviesBase: List<FilmBase>, moviesCache : List<FilmInfoCache>): List<FilmUi>
    class Base() : FavoriteFilmsComparisonMapper {
        override suspend fun compare(moviesBase: List<FilmBase>, moviesCache : List<FilmInfoCache>): List<FilmUi> {
            val items = arrayListOf<FilmUi>()
            moviesBase.map { film ->
                val isFavorite = moviesCache.any { it.filmId == film.getId() }
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