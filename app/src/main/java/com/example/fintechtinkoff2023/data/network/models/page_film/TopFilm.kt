package com.example.fintechtinkoff2023.data.network.models.page_film

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.model.Film

data class TopFilm(
    private val countries: List<Country>,
    private val filmId: Int,
    private val filmLength: String,
    private val genres: List<Genre>,
    private val nameEn: String,
    private val nameRu: String?,
    private val posterUrl: String,
    private val posterUrlPreview: String,
    private val rating: String,
    private val ratingChange: Any,
    private val ratingVoteCount: Int,
    private val year: String,
): Film{
    override suspend fun <T> map(mapper: Film.Mapper<T>): T {
        return mapper.map(filmId, nameRu ?: nameEn, posterUrlPreview, year)
    }
}