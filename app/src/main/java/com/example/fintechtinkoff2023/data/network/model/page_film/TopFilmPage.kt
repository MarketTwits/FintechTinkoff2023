package com.example.fintechtinkoff2023.data.network.model.page_film

import com.google.gson.annotations.SerializedName

data class TopFilmPage(
    @SerializedName("films")
    val topFilms: List<TopFilm>,
    @SerializedName("pagesCount")
    val pagesCount: Int
)