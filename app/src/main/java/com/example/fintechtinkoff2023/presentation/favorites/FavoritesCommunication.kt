package com.example.fintechtinkoff2023.presentation.favorites

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.domain.model.FilmUi

interface FavoritesCommunication : Communication.Mutable<List<FilmUi>> {
    class Base : Communication.Abstract<List<FilmUi>>(), FavoritesCommunication
}