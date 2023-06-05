package com.example.fintechtinkoff2023.data.network.models.page_film

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.model.Film

data class TopFilm(
    val countries: List<Country>,
    val filmId: Int,
    val filmLength: String,
    val genres: List<Genre>,
    val nameEn: String,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingChange: Any,
    val ratingVoteCount: Int,
    val year: String,
): Film{
    override suspend fun <T> map(mapper: Film.Mapper<T>): T {
        return mapper.map(filmId, nameRu ?: nameEn, posterUrlPreview, year)
    }
}