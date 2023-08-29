package com.example.fintechtinkoff2023.core.sl

import androidx.lifecycle.ViewModel
import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.presentation.screens.favorites.FavoriteFilmsModule
import com.example.fintechtinkoff2023.presentation.screens.favorites.FavoritesFilmViewModel
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoModule
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoViewModel
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.error.FilmInfoErrorModule
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.error.FilmInfoErrorViewModel
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.loading.FilmInfoLoadingViewModel
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.success.FilmInfoSuccessModule
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.success.FilmInfoSuccessViewModel
import com.example.fintechtinkoff2023.presentation.screens.main.MainModule
import com.example.fintechtinkoff2023.presentation.screens.main.MainViewModel
import com.example.fintechtinkoff2023.presentation.screens.navigation.NavigationModule
import com.example.fintechtinkoff2023.presentation.screens.navigation.NavigationViewModel
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularFilmsViewModel
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularModule
import com.example.fintechtinkoff2023.presentation.screens.search.SearchFilmsViewModel
import com.example.fintechtinkoff2023.presentation.screens.search.SearchModule
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.loading.FilmInfoLoadingModule

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
            FilmInfoLoadingViewModel::class.java -> FilmInfoLoadingModule(core)
            FilmInfoErrorViewModel::class.java -> FilmInfoErrorModule(core)
            FilmInfoSuccessViewModel::class.java -> FilmInfoSuccessModule(core)
            NavigationViewModel::class.java -> NavigationModule()
            MainViewModel::class.java -> MainModule(core)
            PopularFilmsViewModel::class.java -> PopularModule(core)
            SearchFilmsViewModel::class.java -> SearchModule(core)
            FilmInfoViewModel::class.java -> FilmInfoModule(core)
            FavoritesFilmViewModel::class.java -> FavoriteFilmsModule(core)
            else -> dependencyContainer.module(className)
        }
    }
}