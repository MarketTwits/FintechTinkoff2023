package com.example.fintechtinkoff2023.data.network.models.page_film

import com.google.gson.annotations.SerializedName

data class TopFilmPage(
    @SerializedName("films")
    val topFilms: List<TopFilm>,
    @SerializedName("pagesCount")
    val pagesCount: Int
)