package com.example.fintechtinkoff2023.data.network.model.page_film

import com.example.fintechtinkoff2023.data.network.model.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.model.base_film_model.Genre

data class TopFilm(
    val countries: List<Country>,
    val filmId: Int,
    val filmLength: String,
    val genres: List<Genre>,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingChange: Any,
    val ratingVoteCount: Int,
    val year: String,
)