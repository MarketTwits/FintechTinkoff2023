package com.example.fintechtinkoff2023.data.network.model.search_films

data class SearchFilmsPage(
    val films: List<Film>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
)