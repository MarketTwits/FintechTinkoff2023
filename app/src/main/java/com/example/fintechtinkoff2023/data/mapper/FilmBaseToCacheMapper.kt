package com.example.fintechtinkoff2023.data.mapper
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase

interface FilmBaseToCacheMapper {
    suspend fun map(filmBase: FilmInfoBase): FilmCache
    class Base() : FilmBaseToCacheMapper {
        override suspend fun map(filmBase: FilmInfoBase): FilmCache {
            return filmBase.map(FilmInfo.Mapper.ToCache())
        }
    }
}