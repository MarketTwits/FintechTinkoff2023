package com.example.fintechtinkoff2023.presentation.search

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.domain.model.FilmUi

interface SearchFilmCommunication : Communication.Mutable<List<FilmUi>> {
    class Base : Communication.Abstract<List<FilmUi>>(), SearchFilmCommunication
}
interface CheckStringCommunication : Communication.Mutable<String>{
    class Base : Communication.Abstract<String>(), CheckStringCommunication
}
