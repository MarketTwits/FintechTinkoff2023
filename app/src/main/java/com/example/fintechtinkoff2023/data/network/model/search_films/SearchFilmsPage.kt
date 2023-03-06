package com.example.fintechtinkoff2023.data.network.model.search_films

import com.google.gson.annotations.SerializedName

data class SearchFilmsPage(
    @SerializedName("films")
    val searchFilms: List<SearchFilm>,
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("pagesCount")
    val pagesCount: Int,
    @SerializedName("searchFilmsCountResult")
    val searchFilmsCountResult: Int
)