package com.example.fintechtinkoff2023.presentation.search

import com.example.fintechtinkoff2023.core.wrappers.DispatchersList
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface SearchFilmsLoader {
    fun load(
        coroutineScope: CoroutineScope,
        keywords: String,
    )

    fun itemToCache(
        coroutineScope: CoroutineScope,
        filmUi: FilmUi,
    )

    class Base(
        private val dispatchersList: DispatchersList,
        private val communication: SearchFilmCommunication,
        private val filmsInteractor: FilmInteract,
    ) : SearchFilmsLoader {
        override fun load(coroutineScope: CoroutineScope, keywords: String) {
            coroutineScope.launch(dispatchersList.io()) {
                filmsInteractor.fetchSearchFilms(keywords)
                    .collectLatest {
                        withContext(dispatchersList.main()) {
                            communication.map(it)
                        }
                    }
            }
        }

        override fun itemToCache(coroutineScope: CoroutineScope, filmUi: FilmUi) {
            coroutineScope.launch(dispatchersList.io()) {
                filmsInteractor.addOrRemoveFilm(filmUi)
            }
        }
    }
}