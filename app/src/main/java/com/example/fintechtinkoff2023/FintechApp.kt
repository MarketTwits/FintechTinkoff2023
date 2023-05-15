package com.example.fintechtinkoff2023

import android.app.Application
import com.example.fintechtinkoff2023.data.database.room.AppDatabase
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.base_source.CacheDataSource
import com.example.fintechtinkoff2023.domain.base_source.ItemsSearchComparison
import com.example.fintechtinkoff2023.domain.base_source.ItemsTopComparison
import com.example.fintechtinkoff2023.presentation.favorites.FavoritesFilmViewModel
import com.example.fintechtinkoff2023.presentation.film.FilmInfoViewModel
import com.example.fintechtinkoff2023.presentation.popular.PopularFilmsViewModel
import com.example.fintechtinkoff2023.presentation.search.SearchFilmsViewModel

class FintechApp : Application() {
    lateinit var favoritesFilmViewModel: FavoritesFilmViewModel
    lateinit var popularFilmsViewModel: PopularFilmsViewModel
    lateinit var searchFilmsViewModel: SearchFilmsViewModel
    lateinit var filmInfoViewModel : FilmInfoViewModel
    override fun onCreate() {
        super.onCreate()
        val filmsRepositoryImpl = FilmsRepositoryImpl(
            CacheDataSource.Base(
                AppDatabase.getInstance(this).filmDao()
            ),
            ItemsTopComparison.TopFilmCompare( CacheDataSource.Base(
                AppDatabase.getInstance(this).filmDao()
            )),
            ItemsSearchComparison.SearchFilmCompare(CacheDataSource.Base(AppDatabase.getInstance(this).filmDao()))
        )
        favoritesFilmViewModel = FavoritesFilmViewModel(
           filmsRepositoryImpl
        )
        popularFilmsViewModel = PopularFilmsViewModel(
            filmsRepositoryImpl
        )
        searchFilmsViewModel = SearchFilmsViewModel(
            filmsRepositoryImpl
        )
        filmInfoViewModel = FilmInfoViewModel(
            filmsRepositoryImpl
        )
    }
}