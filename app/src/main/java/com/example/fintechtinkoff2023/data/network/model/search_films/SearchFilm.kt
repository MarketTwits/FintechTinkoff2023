package com.example.fintechtinkoff2023.data.network.model.search_films

import com.example.fintechtinkoff2023.data.network.model.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.model.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.model.Film

data class SearchFilm(
    val countries: List<Country>,
    val description: String,
    val filmId: Int,
    val filmLength: String,
    val genres: List<Genre>,
    val nameEn: String,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val type: String,
    val year: String
) : Film{
    override suspend fun <T> map(mapper: Film.Mapper<T>): T {
        return mapper.map(filmId, nameRu ?: nameEn, posterUrlPreview, year)
    }
}