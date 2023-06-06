package com.example.fintechtinkoff2023.data.mapper

import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.domain.model.Film
import com.example.fintechtinkoff2023.domain.model.FilmBase

interface FilmBaseToCacheMapper {
    suspend fun map(filmBase: FilmBase): FilmCache
    class Base() : FilmBaseToCacheMapper {
        override suspend fun map(filmBase: FilmBase) = filmBase.map(Film.Mapper.ToCache())
    }
}