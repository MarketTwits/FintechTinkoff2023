package com.example.fintechtinkoff2023.presentation.favorites

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.data.network.FilmsCloudDataSource
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.FavoriteFilmsComparisonMapper

class FavoriteFilmsModule(private val core: Core) : Module<FavoritesFilmViewModel> {
    val cacheDataSource = CacheDataSource.Base(core.database().filmDao())
    override fun viewModel() = FavoritesFilmViewModel(
        core.filmsInteractor()
    )
}