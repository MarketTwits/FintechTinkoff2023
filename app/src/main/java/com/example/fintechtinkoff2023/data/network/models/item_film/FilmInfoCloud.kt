package com.example.fintechtinkoff2023.data.network.models.item_film

import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.models.FilmInfo

data class InfoFilmCloudRaw(
    private val completed: Boolean,
    private val countries: List<Country>,
    private val coverUrl: String,
    private val description: String?,
    private val editorAnnotation: Any,
    private val endYear: Any,
    private val filmLength: Int,
    private val genres: List<Genre>,
    private val has3D: Boolean,
    private val hasImax: Boolean,
    private val imdbId: String,
    private val isTicketsAvailable: Boolean,
    private val kinopoiskId: Int,
    private val lastSync: String,
    private val logoUrl: String,
    private val nameEn: String?,
    private val nameOriginal: String,
    private val nameRu: String?,
    private val posterUrl: String,
    private val posterUrlPreview: String,
    private val productionStatus: Any,
    private val ratingAgeLimits: String,
    private val ratingAwait: Any,
    private val ratingAwaitCount: Int,
    private val ratingFilmCritics: Double,
    private val ratingFilmCriticsVoteCount: Int,
    private val ratingGoodReview: Double,
    private val ratingGoodReviewVoteCount: Int,
    private val ratingImdb: Double,
    private val ratingImdbVoteCount: Int,
    private val ratingKinopoisk: Double,
    private val ratingKinopoiskVoteCount: Int,
    private val ratingMpaa: String,
    private val ratingRfCritics: Any,
    private val ratingRfCriticsVoteCount: Int,
    private val reviewsCount: Int,
    private val serial: Boolean,
    private val shortDescription: String,
    private val shortFilm: Boolean,
    private val slogan: String,
    private val startYear: Any,
    private val type: String,
    private val webUrl: String,
    private val year: Int,
) : FilmInfo { //todo add mapper
    override suspend fun <T> map(mapper: FilmInfo.Mapper<T>): T {
        return mapper.map(
            kinopoiskId,
            nameRu ?: nameEn ?: nameOriginal,
            posterUrl,
            description ?: "",
            year.toString(),
            countries,
            genres
        )
    }
}
data class FilmInfoCloud(
    private val kinopoiskId: Int,
    private val name: String,
    private val posterUrl: String,
    private val description : String?,
    private val year: String,
    private val genres: List<Genre>,
    private val countries: List<Country>,
) : FilmInfo {
    override suspend fun <T> map(mapper: FilmInfo.Mapper<T>): T {
        return mapper.map(
            kinopoiskId,
            name,
            posterUrl,
            description ?: "",
            year,
            countries,
            genres
        )
    }
}