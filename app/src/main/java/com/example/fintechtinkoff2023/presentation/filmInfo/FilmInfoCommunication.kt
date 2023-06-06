package com.example.fintechtinkoff2023.presentation.filmInfo

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi


interface FilmInfoCommunication : Communication.Mutable<FilmInfoUi> {
    class Base : Communication.Abstract<FilmInfoUi>(), FilmInfoCommunication
}
interface FilmIdCommunication : Communication.Mutable<Int>{
    class Base : Communication.Abstract<Int>(), FilmIdCommunication
}