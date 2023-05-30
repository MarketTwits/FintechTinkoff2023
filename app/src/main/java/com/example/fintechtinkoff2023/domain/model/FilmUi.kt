package com.example.fintechtinkoff2023.domain.model

import androidx.annotation.DrawableRes
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.data.network.model.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.model.base_film_model.Genre

sealed class FilmUi(
    val filmId : Int = 0,
    val nameRu: String = "",
    val posterUrl: String = "",
    val year: String = "",
    @DrawableRes
    val iconResId: Int = 0,
) {
    open fun getMessage() : String = ""
    class Base(
    filmId : Int, nameRu : String, posterUrl: String, year: String,
    ) : FilmUi(filmId, nameRu, posterUrl, year, 0)
    class Favorite(
        filmId: Int, nameRu : String, posterUrl: String, year: String,
    ) : FilmUi(filmId, nameRu,  posterUrl, year, R.drawable.star)
    class Failed(private val text: String) : FilmUi(){
        override fun getMessage() = text
        class FilmNotFound : FilmUi()
    }
    object Progress : FilmUi()
}