package com.example.fintechtinkoff2023.data.mapper
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.domain.models.FilmInfo
import com.example.fintechtinkoff2023.domain.models.FilmInfoBase

interface FilmBaseToCacheMapper {
    suspend fun map(filmBase: FilmInfoBase): FilmInfoCache
    suspend fun map(filmsBase : List<FilmBase>) : List<FilmCache>
    class Base() : FilmBaseToCacheMapper {
        override suspend fun map(filmBase: FilmInfoBase): FilmInfoCache {
            return filmBase.map(FilmInfo.Mapper.ToCache())
        }
        override suspend fun map(filmsBase: List<FilmBase>): List<FilmCache> {
            return filmsBase.map { it.map(Film.Mapper.ToCache()) }
        }
    }
}