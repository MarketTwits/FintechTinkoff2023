package com.example.fintechtinkoff2023.domain.mapper

import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmBase
import com.example.fintechtinkoff2023.presentation.models.FilmUi

interface FilmUiToDomainFilmMapper {
    suspend fun map(filmUi: FilmUi): FilmBase
    class Base() : FilmUiToDomainFilmMapper {
        override suspend fun map(filmUi: FilmUi) =
            Film.Mapper.ToDomain().map(
                filmUi.filmId,
                filmUi.nameRu,
                filmUi.posterUrl,
                filmUi.year
            )
    }
}