package com.example.fintechtinkoff2023.presentation.popular

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.domain.model.FilmUi

interface PopularFilmCommunication : Communication.Mutable<List<FilmUi>> {
    class Base : Communication.Abstract<List<FilmUi>>(), PopularFilmCommunication
}