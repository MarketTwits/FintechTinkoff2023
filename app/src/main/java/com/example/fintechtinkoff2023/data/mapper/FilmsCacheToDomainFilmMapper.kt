package com.example.fintechtinkoff2023.data.mapper
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase

interface FilmsCacheToDomainFilmMapper {
    suspend fun map(filmCache: FilmCache) : FilmInfoBase
    class Base() : FilmsCacheToDomainFilmMapper{
        override suspend fun map(filmCache: FilmCache): FilmInfoBase {
            return filmCache.map(FilmInfo.Mapper.ToInfoBase())
        }
    }
}