package com.example.fintechtinkoff2023.presentation.screens.search

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.presentation.models.FilmUi

interface SearchFilmCommunication : Communication.Mutable<List<FilmUi>> {
    class Base : Communication.Abstract<List<FilmUi>>(), SearchFilmCommunication
}
interface CheckStringCommunication : Communication.Mutable<String>{
    class Base : Communication.Abstract<String>(), CheckStringCommunication
}
