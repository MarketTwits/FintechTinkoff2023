package com.example.fintechtinkoff2023.presentation.film

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi


interface FilmInfoCommunication : Communication.Mutable<FilmInfoUi> {
    class Base : Communication.Abstract<FilmInfoUi>(), FilmInfoCommunication
}