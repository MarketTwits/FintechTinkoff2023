package com.example.fintechtinkoff2023.core.sl

import androidx.lifecycle.ViewModel
import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.presentation.favorites.FavoriteFilmsModule
import com.example.fintechtinkoff2023.presentation.favorites.FavoritesFilmViewModel
import com.example.fintechtinkoff2023.presentation.film.FilmInfoModule
import com.example.fintechtinkoff2023.presentation.film.FilmInfoViewModel
import com.example.fintechtinkoff2023.presentation.popular.PopularFilmsViewModel
import com.example.fintechtinkoff2023.presentation.popular.PopularModule
import com.example.fintechtinkoff2023.presentation.search.SearchFilmsViewModel
import com.example.fintechtinkoff2023.presentation.search.SearchModule

interface DependencyContainer {
    fun module(className: Class<out ViewModel>): Module<out ViewModel>
    class Error : DependencyContainer {
        override fun module(className: Class<out ViewModel>): Module<out ViewModel> {
            throw IllegalArgumentException("unknown className $className")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {
        override fun module(className: Class<out ViewModel>) = when (className) {
            PopularFilmsViewModel::class.java -> PopularModule(core)
            SearchFilmsViewModel::class.java -> SearchModule(core)
            FilmInfoViewModel::class.java -> FilmInfoModule(core)
            FavoritesFilmViewModel::class.java -> FavoriteFilmsModule(core)
            else -> dependencyContainer.module(className)
        }
    }
}