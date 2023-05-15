package com.example.fintechtinkoff2023.domain.state

import com.example.fintechtinkoff2023.domain.model.Film

interface FilmResult : Film {
    fun toFavorite() : Boolean
    fun isSuccess() : Boolean
    fun errorMessage() : String

    class Success<T>(private val film : Film, private val toFavorite : Boolean) : FilmResult{
        override fun toFavorite(): Boolean = toFavorite
        override fun isSuccess(): Boolean = true
        override fun errorMessage() : String = ""
        override suspend fun <T> map(mapper: Film.Mapper<T>): T = film.map(mapper)

    }
    class Failure(private val error: String) : FilmResult{
        override fun toFavorite(): Boolean = false
        override fun isSuccess(): Boolean = false
        override fun errorMessage() : String = error
        override suspend fun <T> map(mapper: Film.Mapper<T>): T = throw IllegalStateException()
    }
}
