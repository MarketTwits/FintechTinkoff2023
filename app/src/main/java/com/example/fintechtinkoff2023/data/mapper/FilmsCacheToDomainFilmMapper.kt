package com.example.fintechtinkoff2023.data.mapper
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase

interface FilmsCacheToDomainFilmMapper {
    suspend fun map(filmInfoCache: FilmInfoCache) : FilmInfoBase
    class Base() : FilmsCacheToDomainFilmMapper{
        override suspend fun map(filmInfoCache: FilmInfoCache): FilmInfoBase {
            return filmInfoCache.map(FilmInfo.Mapper.ToInfoBase())
        }
    }
}